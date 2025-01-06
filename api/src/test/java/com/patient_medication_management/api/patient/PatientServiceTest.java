package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.dto.responses.AddressDTO;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.mappers.AddressMapper;
import com.patient_medication_management.api.mappers.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private PatientService patientService;

    private PatientDTO patientDTO;
    private Patient patient;
    private Address address;
    List<Patient> patientList;
    List<PatientDTO> patientDTOList;

    @BeforeEach
    void clearDatabaseMocks() {
        reset(patientRepository, addressRepository);
    }
    
    @BeforeEach
    void setUp() {
        AddressDTO addressDTO = new AddressDTO("789 Maple Street", "Apt 12C", "Springfield", "IL", "62704");
        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        patientDTO = new PatientDTO(
                patient.getId(),
                "Alice",
                "Johnson",
                "1992-07-12",
                PatientGender.FEMALE,
                "alice.johnson@example.com",
                "987-654-3210",
                new AddressDTO("789 Maple Street", "Apt 12C", "Springfield", "IL", "62704"),
                0
        );
        address = new Address("789 Maple Street", "Apt 12C", "Springfield", "IL", "62704");
        patient = new Patient(
                uniqueId,
                "Alice",
                "Johnson",
                "alice.johnson@example.com",
                "987-654-3210",
                "1992-07-12",
                PatientGender.FEMALE,
                address
        );

        Patient anotherPatient = new Patient(
                uniqueId,
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "1985-01-01",
                PatientGender.MALE,
                address
        );

        patientList = List.of(patient, anotherPatient);

        PatientDTO anotherPatientDTO = new PatientDTO(
                uniqueId,
                "John",
                "Doe",
                "1985-01-01",
                PatientGender.MALE,
                "john.doe@example.com",
                "123-456-7890",
                new AddressDTO("789 Maple Street", "Apt 12C", "Springfield", "IL", "62704"),
                0
        );

        patientDTOList = List.of(patientDTO, anotherPatientDTO);
    }

    @Test
    void shouldCreatePatientSuccessfully() {
        when(addressMapper.mapToEntity(patientDTO.getAddress())).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(patientMapper.mapToEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.existsById(anyString())).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientMapper.mapToDTO(any(Patient.class))).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
        assertEquals("Johnson", result.getLastName());
        assertEquals("Springfield", result.getAddress().getCity());

        verify(addressMapper, times(1)).mapToEntity(patientDTO.getAddress());
        verify(addressRepository, times(1)).save(address);
        verify(patientMapper, times(1)).mapToEntity(patientDTO);
        verify(patientRepository, times(1)).existsById(anyString());
        verify(patientRepository, times(1)).save(any(Patient.class));
        verify(patientMapper, times(1)).mapToDTO(any(Patient.class));
    }

    @Test
    void shouldReturnPatientList() {
        when(patientRepository.findAll()).thenReturn(patientList);
        when(patientMapper.mapToDTO(patient)).thenReturn(patientDTO);
        when(patientMapper.mapToDTO(patientList.get(1))).thenReturn(patientDTOList.get(1));

        List<PatientDTO> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
        assertEquals("John", result.get(1).getFirstName());

        verify(patientRepository, times(1)).findAll();
        verify(patientMapper, times(1)).mapToDTO(patient);
        verify(patientMapper, times(1)).mapToDTO(patientList.get(1));
    }

    @Test
    void shouldUpdatePatientSuccessfully() {
        AddressDTO updatedAddressDTO = new AddressDTO("123 New Address St", "Apt 10A", "Springfield", "IL", "62705");
        PatientDTO updatedPatientDTO = new PatientDTO(
                patientDTO.getId(), // Keep the same ID as the original patient
                "Alice",
                "Johnson",
                "1992-07-12",
                PatientGender.FEMALE,
                "alice.johnson_updated@example.com", // Updated email
                "987-654-3210",
                updatedAddressDTO,
                1 // Updated some other fields as an example
        );
        Patient updatedPatient = new Patient(
                patient.getId(),
                updatedPatientDTO.getFirstName(),
                updatedPatientDTO.getLastName(),
                updatedPatientDTO.getEmail(),
                updatedPatientDTO.getPhone(),
                updatedPatientDTO.getDob(),
                updatedPatientDTO.getGender(),
                addressMapper.mapToEntity(updatedPatientDTO.getAddress())
        );

        // Mock the methods in the service
        when(patientRepository.existsById(updatedPatientDTO.getId())).thenReturn(true); // Assuming the patient exists
        when(patientMapper.mapToEntity(updatedPatientDTO)).thenReturn(updatedPatient);
        when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);
        when(patientMapper.mapToDTO(updatedPatient)).thenReturn(updatedPatientDTO);

        // Act: Call the updatePatient method
        PatientDTO result = patientService.updatePatient(updatedPatientDTO);

        // Assert: Check the result
        assertNotNull(result);
//        assertEquals(updatedPatientDTO.getFirstName(), result.getFirstName());
//        assertEquals(updatedPatientDTO.getLastName(), result.getLastName());
//        assertEquals(updatedPatientDTO.getEmail(), result.getEmail());
//        assertEquals(updatedPatientDTO.getAddress().getStreet1(), result.getAddress().getStreet1());
//
//        // Verify the service methods were called
//        verify(patientRepository, times(1)).existsById(updatedPatientDTO.getId());
//        verify(patientMapper, times(1)).mapToEntity(updatedPatientDTO);
//        verify(patientRepository, times(1)).save(updatedPatient);
//        verify(patientMapper, times(1)).mapToDTO(updatedPatient);
    }
}
