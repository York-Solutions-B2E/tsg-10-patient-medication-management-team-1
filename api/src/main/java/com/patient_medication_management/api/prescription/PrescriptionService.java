package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.doctor.DoctorRepository;
import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.exception.InvalidPrescriptionStatusException;
import com.patient_medication_management.api.exception.ResourceNotFoundException;
import com.patient_medication_management.api.kafka.PrescriptionEventPublisher;
import com.patient_medication_management.api.kafka.PrescriptionStatusEvent;
import com.patient_medication_management.api.mappers.PrescriptionMapper;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.medication.MedicationRepository;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.patient.PatientRepository;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.pharmacy.PharmacyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.*;


@Service
@Transactional
public class PrescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionService.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PrescriptionMapper prescriptionMapper;

    private final PrescriptionEventPublisher prescriptionEventPublisher;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, PatientRepository patientRepository,
                               MedicationRepository medicationRepository, DoctorRepository doctorRepository,
                               PharmacyRepository pharmacyRepository, PrescriptionMapper prescriptionMapper,
                               PrescriptionEventPublisher prescriptionEventPublisher) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.prescriptionEventPublisher = prescriptionEventPublisher;
    }

    // Fetch filterable list of all prescriptions
    public Page<PrescriptionDTO> getPrescriptions(String filterName, String filterValue, Pageable pageable) {
        Page<Prescription> prescriptions;

        // Handle null or empty filter cases
        if (filterName == null || filterValue == null || filterName.trim().isEmpty() || filterValue.trim().isEmpty()) {
            prescriptions = prescriptionRepository.findAll(pageable);
            return prescriptions.map(prescriptionMapper::toDTO);
        }

        // Handle different filter cases
        prescriptions = switch (filterName) {
            case "prescriptionId" -> prescriptionRepository
                    .findByPrescriptionIdContainingIgnoreCase(filterValue, pageable);

            case "patientId" -> prescriptionRepository
                    .findByPatient_PatientIdContainingIgnoreCase(filterValue, pageable);

            case "patientName" -> prescriptionRepository
                    .findByPatientNameContainingIgnoreCase(filterValue, pageable);

            case "medicationName" -> prescriptionRepository
                    .findByMedicationNameContainingIgnoreCase(filterValue, pageable);

            case "instructions" -> prescriptionRepository
                    .findByInstructionsContainingIgnoreCase(filterValue, pageable);

            case "status" -> handleStatusFilter(filterValue, pageable);

            case "all" -> prescriptionRepository
                    .findAllContaining(filterValue, pageable);

            default -> prescriptionRepository.findAll(pageable);
        };

        return prescriptions.map(prescriptionMapper::toDTO);
    }

    // Create prescription
    public PrescriptionDTO createPrescription(PrescriptionDTO dto) {
        // Validate input
        validatePrescriptionDTO(dto);

        // Fetch all required entities
        var entities = findRequiredEntities(dto);

        Prescription prescription = buildPrescription(dto, entities);
        logger.info("Building new Prescription object with data: {}", dto);
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        // Log the successful saving of the prescription
        logger.info("New Prescription with ID '{}' successfully saved in the database for Patient ID '{}', Status '{}'",
                savedPrescription.getPrescriptionId(),
                savedPrescription.getPatient().getPatientId(),
                savedPrescription.getStatus());

        logger.info("Initiating event publishing for Prescription ID '{}'", savedPrescription.getPrescriptionId());

        try {
            logger.info("Publishing new Prescription event for ID '{}'", savedPrescription.getPrescriptionId());
            prescriptionEventPublisher.publishNewPrescription(prescriptionMapper.toNewPrescriptionEvent(savedPrescription));
            logger.info("Successfully published Prescription event for ID '{}'", savedPrescription.getPrescriptionId());
        } catch (Exception e) {
            logger.error("Failed to publish Prescription event for ID '{}': {}",
                    savedPrescription.getPrescriptionId(),
                    e.getMessage(),
                    e);
        }
        return prescriptionMapper.toDTO(savedPrescription);
    }

    // Cancel prescription
    public PrescriptionDTO cancelPrescription(Long id) {
        logger.info("Initiating cancellation process for Prescription ID '{}'", id);
        // Fetch and validate prescription
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Prescription not found with ID: %s", id);
                    logger.error(message);
                    return new ResourceNotFoundException(message);
                });
        logger.info("Prescription fetched successfully for ID '{}'. Current Status: '{}'",
                prescription.getPrescriptionId(),
                prescription.getStatus());

        if (prescription.getStatus() == PrescriptionStatus.CANCELLED) {
            String message = "Prescription is already cancelled. No action required.";
            logger.warn("Cancellation attempt for ID '{}' failed: {}", id, message);
            throw new IllegalStateException(message);
        }

        if (prescription.getStatus() == PrescriptionStatus.PICKED_UP) {
            String message = "Cannot cancel Prescription ID '{}' because it has already been picked up.";
            logger.warn("Cancellation attempt failed: {}", message);
            throw new IllegalStateException(message);
        }
        logger.info("Updating Prescription ID '{}' to status '{}'",
                prescription.getPrescriptionId(),
                PrescriptionStatus.CANCELLED);

        prescription.setStatus(PrescriptionStatus.CANCELLED);
        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        logger.info("Prescription ID '{}' successfully updated in the database with status '{}'",
                updatedPrescription.getPrescriptionId(),
                updatedPrescription.getStatus());

        logger.info("Publishing cancellation event for Prescription ID '{}'",
                updatedPrescription.getPrescriptionId());

        prescriptionEventPublisher.publishCancelPrescription(prescriptionMapper.toCancelPrescriptionEvent(updatedPrescription));

        logger.info("Successfully published cancellation event for Prescription ID '{}'",
                updatedPrescription.getPrescriptionId());
        return prescriptionMapper.toDTO(updatedPrescription);
    }

    // Update prescription from External Service
    public void updatePrescriptionStatus(PrescriptionStatusEvent event) {
        Prescription prescription = prescriptionRepository.findByPrescriptionId(event.getPrescriptionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Prescription not found with ID: %s", event.getPrescriptionId()))
                );
        prescription.setStatus(event.getStatus());
        logger.info("RECEIVED the Event to update the prescription status");
        prescriptionRepository.save(prescription);
        logger.info("Prescription CANCELLED SUCCESSFULLY!");
    }

    private void validatePrescriptionDTO(PrescriptionDTO dto) {
        if (dto == null) {
            throw new ResourceNotFoundException("Prescription data cannot be null");
        }

        List<String> missingFields = new ArrayList<>();

        if (dto.getPatientId() == null) missingFields.add("patientId");
        if (dto.getMedicationId() == null) missingFields.add("medicationId");
        if (dto.getDoctorId() == null) missingFields.add("doctorId");
        if (dto.getPharmacyId() == null) missingFields.add("pharmacyId");

        if (!missingFields.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Required fields missing: %s", String.join(", ", missingFields))
            );
        }
    }

    @Data
    @AllArgsConstructor
    private static class RequiredEntities {
        private Patient patient;
        private Medication medication;
        private Doctor doctor;
        private Pharmacy pharmacy;
    }

    private RequiredEntities findRequiredEntities(PrescriptionDTO dto) {
        return new RequiredEntities(
                findPatient(dto.getPatientId()),
                findMedication(dto.getMedicationId()),
                findDoctor(dto.getDoctorId()),
                findPharmacy(dto.getPharmacyId())
        );
    }

    private Patient findPatient(String patientId) {
        return patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Patient not found with ID: %s", patientId)
                ));
    }

    private Medication findMedication(Long medicationId) {
        return medicationRepository.findById(medicationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Medication not found with ID: %d", medicationId)
                ));
    }

    private Doctor findDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Doctor not found with ID: %d", doctorId)
                ));
    }

    private Pharmacy findPharmacy(Long pharmacyId) {
        return pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Pharmacy not found with ID: %d", pharmacyId)
                ));
    }

    private Prescription buildPrescription(PrescriptionDTO dto, RequiredEntities entities) {
        return Prescription.builder()
                .prescriptionId(generateUniquePrescriptionId())
                .instructions(dto.getInstructions())
                .status(PrescriptionStatus.SENT)
                .dosage(dto.getDosage())
                .quantity(dto.getQuantity())
                .medication(entities.getMedication())
                .doctor(entities.getDoctor())
                .pharmacy(entities.getPharmacy())
                .patient(entities.getPatient())
                .build();
    }

    private String generateUniquePrescriptionId() {
        String randomAlphanumeric = generateRandomAlphanumeric(3);
        return "PR" + randomAlphanumeric; // Prefix with PR
    }

    private String generateRandomAlphanumeric(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    private Page<Prescription> handleStatusFilter(String statusValue, Pageable pageable) {
        try {
            PrescriptionStatus status = PrescriptionStatus.valueOf(statusValue.toUpperCase());
            return prescriptionRepository.findByStatus(status, pageable);
        } catch (IllegalArgumentException e) {
            throw new InvalidPrescriptionStatusException(
                    String.format("Invalid prescription status: '%s'. Valid status values are: %s",
                            statusValue,
                            Arrays.toString(PrescriptionStatus.values()))
            );
        }
    }

}
