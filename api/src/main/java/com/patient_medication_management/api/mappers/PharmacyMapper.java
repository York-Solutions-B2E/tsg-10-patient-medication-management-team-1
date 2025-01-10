package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.dto.responses.PharmacyDTO;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PharmacyMapper {

    public abstract PharmacyDTO mapToDTO(Pharmacy pharmacy);

    public abstract List<PharmacyDTO> mapToDTOs(List<Pharmacy> pharmacies);

}
