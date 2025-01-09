package com.patient_medication_management.api.kafka;

import com.patient_medication_management.api.enums.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionStatusEvent {
    private String prescriptionId;
    private PrescriptionStatus status;
}
