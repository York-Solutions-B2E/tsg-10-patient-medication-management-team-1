package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mappings({
            @Mapping(target = "street1", source = "address.street1"),
            @Mapping(target = "street2", source = "address.street2"),
            @Mapping(target = "city", source = "address.city"),
            @Mapping(target = "state", source = "address.state"),
            @Mapping(target = "zipCode", source = "address.zipCode")
    })
    public abstract PatientDTO mapToDTO(Patient patient);

    @Mapping(target = "address", ignore = true)
    public abstract Patient mapToEntity(PatientDTO patientDTO);
}
