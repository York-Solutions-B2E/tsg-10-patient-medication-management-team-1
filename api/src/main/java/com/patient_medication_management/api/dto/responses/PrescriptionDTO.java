package com.patient_medication_management.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionDTO {
    private String prescriptionId;
    private String patientId;
    private String medicationCode;
    private String instructions;
    private PrescriptionStatus status;
    private String issueDate;
    private String dosage;
    private Integer quantity;
    private String doctorName;
    private String medicationName;
    private Long medicationId;
    private String pharmacyName;
    private Long pharmacyId;
    private Long doctorId;

    public PrescriptionDTO(String prescriptionId, String patientId, String medicationCode,
                           String instructions, PrescriptionStatus status, String issueDate, String dosage, Integer quantity,
                           String doctorName, String medicationName, String pharmacyName) {
    }
}
