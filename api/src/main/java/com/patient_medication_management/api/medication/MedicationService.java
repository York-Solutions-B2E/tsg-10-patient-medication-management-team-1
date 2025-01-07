package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.mappers.MedicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    public MedicationDTO createMedication(MedicationDTO medicationDTO) {
        if (medicationRepository.existsByMedicationName(medicationDTO.getMedicineName())) {
            throw new IllegalArgumentException("Medication with name " + medicationDTO.getMedicineName() + " already exists.");
        }

        Medication medication = medicationMapper.mapToEntity(medicationDTO);
        Medication savedMedication = medicationRepository.save(medication);
        return medicationMapper.mapToDTO(savedMedication);
    }

    public MedicationDTO getMedicationByName(String medicationName) {
        Optional<Medication> medicationOptional = medicationRepository.findByMedicationName(medicationName);
        if (medicationOptional.isEmpty()) {
            throw new IllegalArgumentException("Medication with name " + medicationName + " not found.");
        }
        return medicationMapper.mapToDTO(medicationOptional.get());
    }

    public List<MedicationDTO> getMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return medicationMapper.mapToDTOs(medications);
    }

}
