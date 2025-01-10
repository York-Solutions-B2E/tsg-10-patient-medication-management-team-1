package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.patient.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicationControllerTest {

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @Test
    public void shouldGetAllMedications() {

        List<MedicationDTO> medications = List.of(
                new MedicationDTO(1L, "Medication 1", "MED001"),
                new MedicationDTO(2L, "Medication 2", "MED002")
        );
        when(medicationService.getMedications()).thenReturn(medications);
        ResponseEntity<List<MedicationDTO>> response = medicationController.getMedications();
        assertEquals(medications, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }
}
