package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrescriptionControllerTests {

    @Mock
    private PrescriptionService prescriptionService;

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Test
    void testCreatePrescription() {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId("RX123");
        prescriptionDTO.setInstructions("Take once a day");
        prescriptionDTO.setStatus(PrescriptionStatus.SENT);
        prescriptionDTO.setIssueDate("2025-01-01");
        prescriptionDTO.setDosage("500mg");
        prescriptionDTO.setQuantity(30);

        PrescriptionDTO createdPrescriptionDTO = new PrescriptionDTO();
        createdPrescriptionDTO.setPrescriptionId("RX123");
        createdPrescriptionDTO.setInstructions("Take once a day");
        createdPrescriptionDTO.setStatus(PrescriptionStatus.SENT);
        createdPrescriptionDTO.setIssueDate("2025-01-01");
        createdPrescriptionDTO.setDosage("500mg");
        createdPrescriptionDTO.setQuantity(30);

        when(prescriptionService.createPrescription(prescriptionDTO)).thenReturn(createdPrescriptionDTO);
        ResponseEntity<PrescriptionDTO> response = prescriptionController.createPrescription(prescriptionDTO);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(createdPrescriptionDTO, response.getBody());
    }

    @Test
    void testGetAllPrescriptions() {
        PrescriptionDTO prescription1 = new PrescriptionDTO();
        prescription1.setPrescriptionId("RX123");
        prescription1.setInstructions("Take once a day");

        PrescriptionDTO prescription2 = new PrescriptionDTO();
        prescription2.setPrescriptionId("RX124");
        prescription2.setInstructions("Take twice a day");

        List<PrescriptionDTO> prescriptions = List.of(prescription1, prescription2);
        Page<PrescriptionDTO> prescriptionPage = new PageImpl<>(prescriptions, PageRequest.of(0, 10), prescriptions.size());

        // Define behavior of mocked service
        when(prescriptionService.getPrescriptions("none", "", PageRequest.of(0, 10))).thenReturn(prescriptionPage);

        // Call the controller method
        ResponseEntity<Page<PrescriptionDTO>> response = prescriptionController.getPrescriptions("none", "", PageRequest.of(0, 10));

        // Assert the status and the content of the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(prescriptionPage, response.getBody());
    }

    @Test
    void testGetPrescriptionsWithFilters() {
        // Setup data
        PrescriptionDTO prescription = new PrescriptionDTO();
        prescription.setPrescriptionId("RX123");
        prescription.setInstructions("Take once a day");

        List<PrescriptionDTO> prescriptions = List.of(prescription);
        Page<PrescriptionDTO> prescriptionPage = new PageImpl<>(prescriptions, PageRequest.of(0, 10), prescriptions.size());

        // Define behavior of mocked service with filters
        when(prescriptionService.getPrescriptions("status", "SENT", PageRequest.of(0, 10))).thenReturn(prescriptionPage);

        // Call the controller method with filters
        ResponseEntity<Page<PrescriptionDTO>> response = prescriptionController.getPrescriptions("status", "SENT", PageRequest.of(0, 10));

        // Assert the status and the content of the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(prescriptionPage, response.getBody());
    }

    @Test
    void testCancelPrescription() {
        Long prescriptionId = 123L;
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId("RX123");
        prescriptionDTO.setStatus(PrescriptionStatus.CANCELLED);

        // Define behavior of mocked service
        when(prescriptionService.cancelPrescription(prescriptionId)).thenReturn(prescriptionDTO);

        // Call the controller method
        ResponseEntity<PrescriptionDTO> response = prescriptionController.cancelPrescription(prescriptionId);

        // Assert the status and the content of the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(prescriptionDTO, response.getBody());
    }
}
