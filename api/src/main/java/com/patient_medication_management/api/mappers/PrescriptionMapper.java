package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.prescription.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(target = "medicationName", source = "medication.medicationName")
    @Mapping(target = "medicationCode", source = "medication.medicationCode")
    @Mapping(target = "doctorName", expression = "java(prescription.getDoctor().getFirstName() + ' ' + prescription.getDoctor().getLastName())")
    @Mapping(target = "pharmacyName", source = "pharmacy.name")
    @Mapping(target = "patientId", source = "patient.id")
    PrescriptionDTO mapToDTO(Prescription prescription);

    @Mapping(target = "medication", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "id", ignore = true)
    Prescription mapToEntity(PrescriptionDTO prescriptionDTO);
}
