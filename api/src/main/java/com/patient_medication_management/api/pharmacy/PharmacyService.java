package com.patient_medication_management.api.pharmacy;

import com.patient_medication_management.api.dto.responses.PharmacyDTO;
import com.patient_medication_management.api.mappers.PharmacyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMapper pharmacyMapper;

    @Autowired
    public PharmacyService(PharmacyRepository pharmacyRepository, PharmacyMapper pharmacyMapper) {
        this.pharmacyRepository = pharmacyRepository;
        this.pharmacyMapper = pharmacyMapper;
    }

    public List<PharmacyDTO> getPharmacies() {
        return pharmacyMapper.mapToDTOs(pharmacyRepository.findAll());
    }
}
