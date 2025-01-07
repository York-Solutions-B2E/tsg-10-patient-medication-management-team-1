package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.medication.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MedicationMapper {

    @Mapping(target = "medicineName", source = "medicationName")
    @Mapping(target = "medicineCode", source = "medicationCode")
    MedicationDTO mapToDTO(Medication medication);

    @Mapping(target = "medicationName", source = "medicineName")
    @Mapping(target = "medicationCode", source = "medicineCode")
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Medication mapToEntity(MedicationDTO medicationDTO);

    @Mapping(target = "medicineName", source = "medicationName")
    @Mapping(target = "medicineCode", source = "medicationCode")
    List<MedicationDTO> mapToDTOs(List<Medication> medications);
}
