package com.patient_medication_management.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrescriptionDTO {
    private String prescriptionId;
    private String patientId;
    private String medicineCode;
    private String instructions;
    private String status;
    private String issueDate;
    private String dosage;
    private Integer quantity;
    private String doctorName;
    private String medicationName;
    private String pharmacyName;

}
