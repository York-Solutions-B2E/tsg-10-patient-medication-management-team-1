package com.patient_medication_management.api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPrescriptionEvent {
    private String patientId;
    private String prescriptionId;
    private String medicationCode;
    private String dosage;
    private Integer quantity;
    private String instructions;
}
