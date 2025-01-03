package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionControllerTest {

    @Mock
    private PrescriptionService prescriptionService;

    @InjectMocks
    private PrescriptionController prescriptionController;

    private PrescriptionDTO prescriptionDTO;
    private PrescriptionDTO expectedResponse;

    @BeforeEach
    void setUp() {

        // Entry data
        prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId("f2093hs3");
        prescriptionDTO.setPatientId("8fa05b96");
        prescriptionDTO.setMedicationId(1L);
        prescriptionDTO.setDoctorId(1L);
        prescriptionDTO.setPharmacyId(1L);
        prescriptionDTO.setInstructions("Take twice daily");
        prescriptionDTO.setStatus(PrescriptionStatus.READY_FOR_PICKUP);
        prescriptionDTO.setIssueDate("2025-01-03");
        prescriptionDTO.setDosage("10mg");
        prescriptionDTO.setQuantity(30);

        // Expected response data
        expectedResponse = new PrescriptionDTO();
        expectedResponse.setPrescriptionId("f2093hs3");
        expectedResponse.setPatientId("8fa05b96");
        expectedResponse.setMedicationId(1L);
        expectedResponse.setMedicationName("Aspirin");
        expectedResponse.setMedicationCode("MED123");
        expectedResponse.setDoctorId(1L);
        expectedResponse.setDoctorName("John Smith");
        expectedResponse.setPharmacyName("CVS");
        expectedResponse.setStatus(PrescriptionStatus.READY_FOR_PICKUP);
        expectedResponse.setIssueDate("2025-01-03");
        expectedResponse.setDosage("10mg");
        expectedResponse.setQuantity(30);
    }

    @Test
    void shouldCreatePrescription() {
        // Arrange
        when(prescriptionService.createPrescription(any(PrescriptionDTO.class))).thenReturn(expectedResponse);

        // Assert
        ResponseEntity<PrescriptionDTO> response = prescriptionController.createPrescription(prescriptionDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(prescriptionService, times(1)).createPrescription(prescriptionDTO);
    }
}
