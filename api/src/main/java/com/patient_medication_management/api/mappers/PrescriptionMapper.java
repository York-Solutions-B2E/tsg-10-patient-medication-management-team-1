package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.prescription.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    default String getFullName(String firstName, String lastName) {
        if (firstName == null && lastName == null) return null;
        StringBuilder name = new StringBuilder();
        if (firstName != null) name.append(firstName);
        if (firstName != null && lastName != null) name.append(" ");
        if (lastName != null) name.append(lastName);
        return name.length() > 0 ? name.toString() : null;
    }

    @Mapping(target = "doctorName", expression = "java(prescription.getDoctor() == null ? null : getFullName(prescription.getDoctor().getFirstName(), prescription.getDoctor().getLastName()))")
    @Mapping(target = "patientName", expression = "java(prescription.getPatient() == null ? null : getFullName(prescription.getPatient().getFirstName(), prescription.getPatient().getLastName()))")
    @Mapping(target = "patientId", source = "patient.patientId")
    @Mapping(target = "medicationId", source = "medication.id")
    @Mapping(target = "medicationName", source = "medication.medicationName")
    @Mapping(target = "medicationCode", source = "medication.medicationCode")
    @Mapping(target = "pharmacyId", source = "pharmacy.id")
    @Mapping(target = "pharmacyName", source = "pharmacy.name")
    @Mapping(target = "doctorId", source = "doctor.id")
    public abstract PrescriptionDTO toDTO(Prescription prescription);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medication", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Prescription toEntity(PrescriptionDTO dto);
}