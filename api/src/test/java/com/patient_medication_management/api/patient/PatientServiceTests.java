package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.mappers.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Pageable pageable;
    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageable = PageRequest.of(1, 10); // Example pageable

        // Setup mock patient data
        patient = new Patient();

        // Use setters to assign values to the Patient object
        patient.setPatientId("1");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john.doe@example.com");
        patient.setPhone("555-5555");
        patient.setDob("1990-01-01");
        patient.setGender(PatientGender.MALE);
        patient.setAddress(null);  // Set address to null or any valid Address object

        // Initialize the PatientDTO with setter values (if needed)
        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");
        patientDTO.setEmail("john.doe@example.com");
        patientDTO.setPhone("555-5555");
        patientDTO.setDob("1990-01-01");
        patientDTO.setGender(PatientGender.MALE);
    }

    @Test
    void testGetPatientsWithSearchTerm() {
        // Setup mock repository to return a page of patients
        Page<Patient> mockPage = new PageImpl<>(List.of(patient));
        when(patientRepository.findByFirstNameContainingIgnoreCase("John", pageable)).thenReturn(mockPage);

        // Setup mock mapper to return a page of patient DTOs
        when(patientMapper.mapToDTO(patient)).thenReturn(patientDTO);

        // Call the method
        Page<PatientDTO> result = patientService.getPatients("firstName", "John", pageable);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }

    @Test
    void testGetPatientsWithoutFilter() {
        // Setup mock repository to return a page of patients when no search term is provided
        Page<Patient> mockPage = new PageImpl<>(List.of(patient));
        when(patientRepository.findAll(pageable)).thenReturn(mockPage);

        // Setup mock mapper to return a page of patient DTOs
        when(patientMapper.mapToDTO(patient)).thenReturn(patientDTO);

        // Call the method
        Page<PatientDTO> result = patientService.getPatients("none", "", pageable);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }
}
