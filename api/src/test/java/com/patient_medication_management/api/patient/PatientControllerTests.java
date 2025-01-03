package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {



    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void shouldGetFirstPageWithNoFilter() throws Exception {
        List<PatientDTO> patients = getPatientDTOS();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PatientDTO> page = new PageImpl<>(patients, pageable, patients.size());

        when(patientService.getPatients(eq("none"), eq(""), any(Pageable.class))).thenReturn(page);
        ResponseEntity<Page<PatientDTO>> response = patientController.getPatients("none", "", pageable);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("12345678", Objects.requireNonNull(response.getBody()).getContent().get(0).getId());
        assertEquals(0L, response.getBody().getPageable().getPageNumber());


    }

    @Test
    public void shouldGetFirstPageWithFilter() throws Exception {
        List<PatientDTO> patients = getPatientDTOS();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PatientDTO> page = new PageImpl<>(patients, pageable, patients.size());

        when(patientService.getPatients(anyString(), anyString(), any(Pageable.class))).thenReturn(page);
        ResponseEntity<Page<PatientDTO>> response = patientController.getPatients("firstName", "John", pageable);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("12345678", Objects.requireNonNull(response.getBody()).getContent().get(0).getId());
        assertEquals(0L, response.getBody().getPageable().getPageNumber());
        assertNotEquals(2L, response.getBody().getTotalPages());

    }

    private static List<PatientDTO> getPatientDTOS() {
        List<String> pharmacyNames = List.of("CVS", "Walgreens");
        return List.of(
                new PatientDTO("12345678", "John", "Doe", "2000-01-01", PatientGender.MALE, "email@email.com", "1234567890", "123 Main St", "Apt 1", "Springfield", "IL", "62701", pharmacyNames, 32L),
                new PatientDTO("87654321", "Jane", "Smith", "1990-01-01", PatientGender.FEMALE, "email@email.com", "1234567890", "123 Main St", "Apt 1", "Springfield", "IL", "62701", pharmacyNames, 12L)
        );
    }


}
