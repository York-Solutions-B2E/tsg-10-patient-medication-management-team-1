package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.dto.responses.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patients", ignore = true)
    public abstract Address mapToEntity(AddressDTO addressDTO);

    public abstract AddressDTO mapToDTO(Address address);
}
