package com.patient_medication_management.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;
    private String oktaId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
