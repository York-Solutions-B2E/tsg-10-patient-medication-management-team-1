package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.doctor.DoctorRepository;
import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.mappers.PrescriptionMapper;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.medication.MedicationRepository;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.patient.PatientRepository;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.pharmacy.PharmacyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, PatientRepository patientRepository,
                               MedicationRepository medicationRepository, DoctorRepository doctorRepository,
                               PharmacyRepository pharmacyRepository, PrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.prescriptionMapper = prescriptionMapper;
    }

    public List<PrescriptionDTO> getPrescriptionsByPatientId(String patientId) {
        // Fetch prescriptions from database by patient ID
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId);
        // Convert list of prescriptions to stream for transformation
        return prescriptions.stream()
                // Map each prescription entity to PrescriptionDTO
                .map(p -> new PrescriptionDTO(
                        p.getPrescriptionId(),
                        p.getPatient().getId(),
                        p.getMedication().getMedicationCode(),
                        p.getInstructions(),
                        p.getStatus(),
                        p.getIssueDate(),
                        p.getDosage(),
                        p.getQuantity(),
                        p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName(),
                        p.getMedication().getMedicationName(),
                        p.getPharmacy() != null ? p.getPharmacy().getName() : null // (if pharmacy exists)
                ))
                // Collect mapped PrescriptionDTO back to list
                .collect(Collectors.toList());
    }

    // Create a new prescription
    @Transactional
    public PrescriptionDTO createPrescription(PrescriptionDTO prescriptionDTO) {
        // Retrieve the Patient
        Patient patient = patientRepository.findById(prescriptionDTO.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Retrieve the Medication
        Medication medication = medicationRepository.findById(prescriptionDTO.getMedicationId())
                .orElseThrow(() -> new IllegalArgumentException("Medication not found"));

        // Retrieve the Doctor
        Doctor doctor = doctorRepository.findById(prescriptionDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Retrieve the Pharmacy
        Pharmacy pharmacy = pharmacyRepository.findById(prescriptionDTO.getPharmacyId())
                .orElseThrow(() -> new IllegalArgumentException("Pharmacy not found"));

        // Generate unique prescription ID
        String uniquePrescriptionId = generateUniquePrescriptionId();

        // Create a new Prescription
        Prescription prescription = new Prescription(
                prescriptionDTO.getInstructions(),
                uniquePrescriptionId,
                PrescriptionStatus.valueOf(prescriptionDTO.getStatus().name()),
                prescriptionDTO.getIssueDate(),
                prescriptionDTO.getDosage(),
                prescriptionDTO.getQuantity(),
                medication,
                doctor,
                pharmacy,
                patient
        );

        // Save the Prescription
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        // Map to DTO and include additional details
        PrescriptionDTO responseDTO = prescriptionMapper.mapToDTO(savedPrescription);

        // Populate additional details for response
        responseDTO.setMedicationName(medication.getMedicationName());
        responseDTO.setMedicationCode(medication.getMedicationCode());
        responseDTO.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
        responseDTO.setPharmacyName(pharmacy.getName());

        return responseDTO;
    }

    private String generateUniquePrescriptionId() {
        return java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
