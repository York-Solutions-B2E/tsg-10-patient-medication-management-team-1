package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.medication.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    @Mapping(target = "name", source = "medicationName")
    @Mapping(target = "medicineCode", source = "medicationCode")
    public abstract MedicationDTO mapToDTO(Medication medication);

    @Mapping(target = "medicationName", source = "name")
    @Mapping(target = "medicationCode", source = "medicineCode")
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Medication mapToEntity(MedicationDTO medicationDTO);
}
