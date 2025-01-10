package com.patient_medication_management.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String fax;
}
