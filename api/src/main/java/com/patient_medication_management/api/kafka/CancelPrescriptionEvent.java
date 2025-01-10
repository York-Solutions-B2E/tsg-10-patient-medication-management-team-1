package com.patient_medication_management.api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelPrescriptionEvent {
    private String prescriptionId;
}
