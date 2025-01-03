package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.mappers.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static com.nimbusds.oauth2.sdk.util.StringUtils.isNumeric;

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
        // Create and save the Address
        Address address = new Address();
        address.setStreet1((patientDTO.getStreet1()));
        address.setStreet2(patientDTO.getStreet1());
        address.setCity(patientDTO.getCity());
        address.setState(patientDTO.getState());
        address.setZipCode(patientDTO.getZipCode());

        Address savedAddress = addressRepository.save(address);

        // Map DTO to Entity and set Address
        Patient patient = patientMapper.mapToEntity(patientDTO);
        patient.setAddress(savedAddress);

        // Generate a unique UUID for the Patient ID

        String uniqueId;
        do {
            uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (patientRepository.existsById(uniqueId)); // Check if it already exists

        patient.setId(uniqueId);

        // Save the Patient entity
        Patient savedPatient = patientRepository.save(patient);

        // Map Entity to DTO for response
        return patientMapper.mapToDTO(savedPatient);
    }

    public Page<PatientDTO> getPatients(String filterName, String filterValue, Pageable pageable) {
        Page<Patient> patients;

        switch (filterName) {
            case "id":
                patients = patientRepository.findByIdContainingIgnoreCase(filterValue, pageable);
            case "firstName":
                patients = patientRepository.findByFirstNameContainingIgnoreCase(filterValue, pageable);
                break;
            case "lastName":
                patients = patientRepository.findByLastNameContaining(filterValue, pageable);
                break;
            case "dob":
                patients = patientRepository.findByDobContaining(filterValue, pageable);
                break;
            case "gender":
                patients = patientRepository.findByGenderContaining(filterValue, pageable);
                break;
            case "phone":
                patients = patientRepository.findByPhoneContaining(filterValue, pageable);
                break;
            case "email":
                patients = patientRepository.findByEmailContaining(filterValue, pageable);
                break;
            case "address":
                patients = patientRepository.findByAddressContaining(filterValue, pageable);
                break;
            case "pharmacy":
                patients = patientRepository.findByPharmacyContaining(filterValue, pageable);
                break;
            case "all":
                patients = patientRepository.findAllContaining(filterName, pageable);
                break;
            default:
                patients = patientRepository.findAll(pageable);
                break;
        }

        return patients.map(patientMapper::mapToDTO);
    }
}
