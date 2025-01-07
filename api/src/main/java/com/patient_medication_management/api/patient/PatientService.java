package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.mappers.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressRepository addressRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, AddressRepository addressRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.patientMapper = patientMapper;
    }

    // Create a new Patient
    public PatientDTO createPatient(PatientDTO patientDTO) {
        // Get or create address
        Address address = addressRepository.findByStreet1AndStreet2AndCityAndStateAndZipCode(
                patientDTO.getStreet1(),
                patientDTO.getStreet2(),
                patientDTO.getCity(),
                patientDTO.getState(),
                patientDTO.getZipCode()
        ).orElseGet(() -> {
            Address newAddress = new Address();
            newAddress.setStreet1(patientDTO.getStreet1());
            newAddress.setStreet2(patientDTO.getStreet2());
            newAddress.setCity(patientDTO.getCity());
            newAddress.setState(patientDTO.getState());
            newAddress.setZipCode(patientDTO.getZipCode());
            return addressRepository.save(newAddress);
        });

        // Map DTO to Entity and set Address
        Patient patient = patientMapper.mapToEntity(patientDTO);
        patient.setAddress(address);

        // Generate a unique UUID for the Patient ID

        String uniqueId;
        do {
            uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (patientRepository.existsByPatientId(uniqueId)); // Check if it already exists

        patient.setPatientId(uniqueId);

        // Save the Patient entity
        Patient savedPatient = patientRepository.save(patient);

        // Map Entity to DTO for response
        return patientMapper.mapToDTO(savedPatient);
    }

    public Page<PatientDTO> getPatients(String filterName, String filterValue, Pageable pageable) {
        Page<Patient> patients;

    // Handle null or empty filter cases
    if (filterName == null || filterValue == null || filterName.trim().isEmpty() || filterValue.trim().isEmpty()) {
            patients = patientRepository.findAll(pageable);
            return patients.map(patientMapper::mapToDTO);
        }
        switch (filterName) {
            case "id":
                patients = patientRepository.findByPatientIdContainingIgnoreCase(filterValue, pageable);
            case "firstName":
                patients = patientRepository.findByFirstNameContainingIgnoreCase(filterValue, pageable);
                break;
            case "lastName":
                patients = patientRepository.findByLastNameContainingIgnoreCase(filterValue, pageable);
                break;
            case "dob":
                patients = patientRepository.findByDobContaining(filterValue, pageable);
                break;
            case "gender":
                patients = patientRepository.findByGenderContainingIgnoreCase(filterValue, pageable);
                break;
            case "phone":
                patients = patientRepository.findByPhoneContaining(filterValue, pageable);
                break;
            case "email":
                patients = patientRepository.findByEmailContainingIgnoreCase(filterValue, pageable);
                break;
            case "address":
                patients = patientRepository.findByAddressContainingIgnoreCase(filterValue, pageable);
                break;
            case "all":
                patients = patientRepository.findAllContaining(filterValue, pageable);
                break;
            default:
                patients = patientRepository.findAll(pageable);
                break;
        }

        return patients.map(patientMapper::mapToDTO);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {

        Patient existingPatient = patientRepository.findById(patientDTO.getId()).orElseThrow(() ->
                new IllegalArgumentException("Patient not found with ID: " + patientDTO.getId()));

        Address address = addressRepository.findByStreet1AndStreet2AndCityAndStateAndZipCode(
                patientDTO.getStreet1(),
                patientDTO.getStreet2(),
                patientDTO.getCity(),
                patientDTO.getState(),
                patientDTO.getZipCode()
        ).orElseGet(() -> {
            Address newAddress = new Address();
            newAddress.setStreet1(patientDTO.getStreet1());
            newAddress.setStreet2(patientDTO.getStreet2());
            newAddress.setCity(patientDTO.getCity());
            newAddress.setState(patientDTO.getState());
            newAddress.setZipCode(patientDTO.getZipCode());
            return addressRepository.save(newAddress);
        });
        // Update fields of the existing Patient entity
        existingPatient.setFirstName(patientDTO.getFirstName());
        existingPatient.setLastName(patientDTO.getLastName());
        existingPatient.setDob(patientDTO.getDob());
        existingPatient.setGender(patientDTO.getGender());
        existingPatient.setEmail(patientDTO.getEmail());
        existingPatient.setPhone(patientDTO.getPhone());

        existingPatient.setAddress(address);

        // Save the updated Patient and return the mapped DTO
        return patientMapper.mapToDTO(patientRepository.save(existingPatient));

    }

public void deletePatient(String id) {
        patientRepository.deleteByPatientId(id);
    }

}
