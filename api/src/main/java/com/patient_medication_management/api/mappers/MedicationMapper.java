package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.medication.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MedicationMapper {



    MedicationDTO mapToDTO(Medication medication);


    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Medication mapToEntity(MedicationDTO medicationDTO);


    List<MedicationDTO> mapToDTOs(List<Medication> medications);
}
