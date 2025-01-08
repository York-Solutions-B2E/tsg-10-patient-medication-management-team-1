package com.patient_medication_management.api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPrescriptionEvent {
    private String prescriptionId;
    private String patientId;
    private String dosage;
    private Integer quantity;
    private String medicationCode;
    private String instructions;
}
