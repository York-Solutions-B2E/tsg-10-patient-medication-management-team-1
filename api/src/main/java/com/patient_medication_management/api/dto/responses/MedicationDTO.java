package com.patient_medication_management.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDTO {
    private Long id;
    private String medicationName;
    private String medicationCode;
}
