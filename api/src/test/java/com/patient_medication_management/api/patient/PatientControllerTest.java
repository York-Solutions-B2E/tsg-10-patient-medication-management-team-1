package com.patient_medication_management.api.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.dto.responses.AddressDTO;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.mappers.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientMapper patientMapper;

    private ObjectMapper objectMapper;

    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();

        // Initialize MockMvc to simulate the HTTP request
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

        // Initialize PatientDTO for testing

        Address address = new Address();
        address.setStreet1("789 Maple Street");
        address.setStreet2("Apt 12C");
        address.setCity("Springfield");
        address.setState("IL");
        address.setZipCode("62704");

        AddressDTO addressDTO = new AddressDTO(
                address.getStreet1(),
                address.getStreet2(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );

        Patient patient = new Patient();
        patient.setId("8958fcc7");
        patient.setFirstName("Alice");
        patient.setLastName("Johnson");
        patient.setDob("1992-07-12");
        patient.setGender(PatientGender.FEMALE);
        patient.setEmail("alice.johnson@example.com");
        patient.setPhone("987-654-3210");
        patient.setAddress(address);

        PatientDTO patientDTO = new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getId(),
                patient.getGender(),
                patient.getEmail(),
                patient.getPhone(),
                addressDTO,
                0
        );


    }

    @Test
    void shouldReturnCreatedPatient() throws Exception {
        // Simulate the service response

        when(patientService.createPatient(Mockito.any(PatientDTO.class))).thenReturn(patientDTO);

        // Perform a POST request
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Johnson"))
                .andExpect(jsonPath("$.email").value("alice.johnson@example.com"))
                .andExpect(jsonPath("$.address.street1").value("789 Maple Street"));

        // Verify that the service was called
        verify(patientService, times(1)).createPatient(any(PatientDTO.class));
    }

    @Test
    void getAllPatients_shouldReturnPatientList() throws Exception {

        AddressDTO address1 = new AddressDTO("123 Main St", "Apt 4B", "Boston", "MA", "02108");
        PatientDTO patient1 = new PatientDTO("1a2b3c4d", "John", "Doe", "1980-01-01", PatientGender.MALE,
                "john.doe@example.com", "555-1234", address1, 2);

        AddressDTO address2 = new AddressDTO("456 Park Ave", "Suite 201", "Cambridge", "MA", "02139");
        PatientDTO patient2 = new PatientDTO("5e6f7g8h", "Jane", "Smith", "1990-02-15", PatientGender.FEMALE,
                "jane.smith@example.com", "555-5678", address2, 1);

        List<PatientDTO> patientDTOList = Arrays.asList(patient1, patient2);

        when(patientService.getAllPatients()).thenReturn(patientDTOList);

        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[0].address.city").value("Boston"))
                .andExpect(jsonPath("$[1].address.city").value("Cambridge"));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void updatePatient_shouldUpdatePatient() throws Exception {
        AddressDTO updatedAddressDTO = new AddressDTO(
                "123 New Address St",
                "Apt 10A",
                "Springfield",
                "IL",
                "62705"
        );
        PatientDTO updatedPatientDTO = new PatientDTO(
                "8958fcc7", // Same ID as the original patient
                "Alice",
                "Johnson",
                "1992-07-12",
                PatientGender.FEMALE,
                "alice.johnson_updated@example.com", // Updated email
                "987-654-3210",
                updatedAddressDTO,
                1 // Changed value for illustration
        );

        when(patientService.updatePatient(Mockito.any(PatientDTO.class)))
                .thenReturn(updatedPatientDTO);

        mockMvc.perform(put("/api/patients/{id}", "8958fcc7")  // Assuming {id} is the patient ID path variable
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatientDTO)))
                .andExpect(status().isOk())  // Check for status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Check content type
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Johnson"))
                .andExpect(jsonPath("$.email").value("alice.johnson_updated@example.com"))
                .andExpect(jsonPath("$.address.street1").value("123 New Address St"));

        // Verify that the service was called with the correct parameters
        verify(patientService, times(1)).updatePatient(any(PatientDTO.class));
    }

}
