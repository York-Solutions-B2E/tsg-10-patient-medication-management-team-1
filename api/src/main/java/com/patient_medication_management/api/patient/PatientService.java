package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.mappers.AddressMapper;
import com.patient_medication_management.api.mappers.PatientMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressRepository addressRepository;
    private final PatientMapper patientMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, AddressRepository addressRepository, PatientMapper patientMapper, AddressMapper addressMapper) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.patientMapper = patientMapper;
        this.addressMapper = addressMapper;
    }

    // Fetch all the Patients
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    // Create a new Patient
    @Transactional
    public PatientDTO createPatient(PatientDTO patientDTO) {

        if (patientDTO == null || patientDTO.getAddress() == null) {
            throw new IllegalArgumentException("Patient or address data missing");
        }
        // Map AddressDTO to Address Entity
        Address address = addressMapper.mapToEntity(patientDTO.getAddress());
        Address savedAddress = addressRepository.save(address);

        // Map PatientDTO to Patient Entity and set Address
        Patient patient = patientMapper.mapToEntity(patientDTO);
        patient.setAddress(savedAddress);

        // Generate a unique UUID for the Patient ID
        String uniqueId;
        do {
            uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (patientRepository.existsById(uniqueId)); // Check if it already exists
        patient.setId(uniqueId);

        // Save the Patient entity in the db
        Patient savedPatient = patientRepository.save(patient);

        // Map Entity to DTO for response
        return patientMapper.mapToDTO(savedPatient);
    }

    public PatientDTO getPatientById(String id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            throw new IllegalArgumentException("Patient with ID " + id + " not found.");
        }
        return patientMapper.mapToDTO(patientOptional.get());
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        if (!patientRepository.existsById(patientDTO.getId())) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientDTO.getId());
        }
        Patient existingPatient = patientRepository.findById(patientDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientDTO.getId()));

        // Update fields of the existing Patient entity
        existingPatient.setFirstName(patientDTO.getFirstName());
        existingPatient.setLastName(patientDTO.getLastName());
        existingPatient.setDob(patientDTO.getDob());
        existingPatient.setGender(patientDTO.getGender());
        existingPatient.setEmail(patientDTO.getEmail());
        existingPatient.setPhone(patientDTO.getPhone());

        // Update address if needed
        if (patientDTO.getAddress() != null) {
            Address updatedAddress = addressMapper.mapToEntity(patientDTO.getAddress());
            existingPatient.setAddress(updatedAddress);
        }

        // Save the updated Patient entity to the repository
        Patient updatedPatient = patientRepository.save(existingPatient);

        // Map the updated Patient entity back to PatientDTO and return it
        return patientMapper.mapToDTO(updatedPatient);

    }
}
