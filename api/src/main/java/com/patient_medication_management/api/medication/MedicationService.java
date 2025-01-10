package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.mappers.MedicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    public List<MedicationDTO> getMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return medicationMapper.mapToDTOs(medications);
    }

}
