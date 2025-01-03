package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "address", source = "address")
    @Mapping(target = "prescriptionCount", expression = "java(patient.getPrescriptions() != null ? patient.getPrescriptions().size() : 0)")
    public abstract PatientDTO mapToDTO(Patient patient);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Patient mapToEntity(PatientDTO patientDTO);
}
