package com.patient_medication_management.api.dto.responses;

import com.patient_medication_management.api.enums.PatientGender;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
public class PatientDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String dob;
    private PatientGender gender;
    private String email;
    private String phone;
    private AddressDTO address;
    private int prescriptionCount;
}
