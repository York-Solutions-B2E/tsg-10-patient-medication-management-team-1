package com.patient_medication_management.api.dto.responses;

import com.patient_medication_management.api.enums.PatientGender;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private String patientId;
    private String firstName;
    private String lastName;
    private String dob;
    private PatientGender gender;
    private String email;
    private String phone;
    // Address Fields
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipCode;

    private Long prescriptionCount;
}
