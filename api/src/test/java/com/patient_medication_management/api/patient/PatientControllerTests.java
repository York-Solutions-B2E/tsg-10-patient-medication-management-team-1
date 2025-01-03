package com.patient_medication_management.api.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patient_medication_management.api.configuration.SecurityConfig;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
//@WithMockUser(username = "testuser", roles = "USER")
class PatientControllerTest {



    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void testGetPatients_withoutSearchTerm() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        // Creating PatientDTO objects
        List<PatientDTO>
        patient1.setId("1");
        patient1.setFirstName("John");
        patient1.setLastName("Doe");

        PatientDTO patient2 = new PatientDTO();
        patient2.setId("2");
        patient2.setFirstName("Jane");
        patient2.setLastName("Doe");

        List<PatientDTO> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);

// Creating a Page of PatientDTO objects
        Page<PatientDTO> mockPatients = new PageImpl<>(patients, PageRequest.of(0, 10), patients.size());

        when(patientService.getPatients(eq(null), eq(pageable))).thenReturn(mockPatients);

        // Act & Assert
        mockMvc.perform(get("/api/patients")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));
    }


    @Test
    public void testGetPatients_withPartialSearchTerm() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Create sample PatientDTO objects
        PatientDTO patient1 = new PatientDTO();
        patient1.setId("1");
        patient1.setFirstName("John");
        patient1.setLastName("Doe");

        PatientDTO patient2 = new PatientDTO();
        patient2.setId("2");
        patient2.setFirstName("Jane");
        patient2.setLastName("Doe");

        PatientDTO patient3 = new PatientDTO();
        patient3.setId("3");
        patient3.setFirstName("Jack");
        patient3.setLastName("Smith");

        // Only include "Jane" and "Jack" because their names start with "ja"
        List<PatientDTO> patients = new ArrayList<>();
        patients.add(patient2);  // "Jane"
        patients.add(patient3);  // "Jack"

        // Create a Page of PatientDTO objects
        Page<PatientDTO> mockPatients = new PageImpl<>(patients, pageable, patients.size());

        // Mock the patient service to return the mock patients when a partial search term "ja" is passed
        when(patientService.getPatients(eq("ja"), eq(pageable))).thenReturn(mockPatients);

        // Act & Assert
        mockMvc.perform(get("/api/patients")
                        .param("page", "1")
                        .param("size", "10")
                        .param("searchTerm", "ja"))  // Partial search term
                .andExpect(status().isOk())  // Expect status 200
                .andExpect(jsonPath("$.content[0].firstName").value("Jane"))  // Validate first name "Jane"
                .andExpect(jsonPath("$.content[1].firstName").value("Jack"));  // Validate first name "Jack"
                 // "John" should not appear
    }


}
