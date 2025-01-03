package com.patient_medication_management.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {

    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipCode;
}
