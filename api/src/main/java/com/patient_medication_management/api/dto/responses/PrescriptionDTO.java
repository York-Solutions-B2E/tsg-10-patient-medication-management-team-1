package com.patient_medication_management.api.dto.responses;

import com.patient_medication_management.api.enums.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private Long id;
    private String prescriptionId;
    private String patientId;
    private String patientName;
    private Long medicationId;
    private String medicationName;
    private String medicationCode;
    private String instructions;
    private PrescriptionStatus status;
    private String issueDate;
    private String dosage;
    private Integer quantity;
    private Long doctorId;
    private String doctorName;
    private Long pharmacyId;
    private String pharmacyName;
}
