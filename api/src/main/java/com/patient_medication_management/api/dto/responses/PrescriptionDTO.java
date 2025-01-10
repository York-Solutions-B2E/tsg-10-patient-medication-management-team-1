package com.patient_medication_management.api.dto.responses;

import com.patient_medication_management.api.enums.PrescriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

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
    private String dosage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer quantity;
    private Long doctorId;
    private String doctorName;
    private Long pharmacyId;
    private String pharmacyName;
}
