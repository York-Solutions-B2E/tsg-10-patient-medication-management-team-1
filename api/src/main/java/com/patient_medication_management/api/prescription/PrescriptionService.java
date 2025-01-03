package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
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
                        p.getMedication().getMedicineCode(),
                        p.getInstructions(),
                        p.getStatus().name(),
                        p.getIssueDate(),
                        p.getDosage(),
                        p.getQuantity(),
                        p.getDoctor().getFirstName() + " " + p.getDoctor().getLastName(),
                        p.getMedication().getName(),
                        p.getPharmacy() != null ? p.getPharmacy().getName() : null // (if pharmacy exists)
                ))
                // Collect mapped PrescriptionDTO back to list
                .collect(Collectors.toList());
    }
}
