package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.exception.ResourceNotFoundException;
import com.patient_medication_management.api.kafka.NewPrescriptionEvent;
import com.patient_medication_management.api.kafka.PrescriptionEventPublisher;
import com.patient_medication_management.api.kafka.PrescriptionStatusEvent;
import com.patient_medication_management.api.mappers.PrescriptionMapper;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTests {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionEventPublisher prescriptionEventPublisher;


    @Mock
    private Pageable pageable;
    @Test
    void testGetPrescriptions_WithPatientNameFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findByPatientNameContainingIgnoreCase("John Doe", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("patientName", "John Doe", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByPatientNameContainingIgnoreCase("John Doe", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithMedicationNameFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findByMedicationNameContainingIgnoreCase("Aspirin", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("medicationName", "Aspirin", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByMedicationNameContainingIgnoreCase("Aspirin", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithInstructionsFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findByInstructionsContainingIgnoreCase("Take twice daily", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("instructions", "Take twice daily", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByInstructionsContainingIgnoreCase("Take twice daily", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithStatusFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository call inside handleStatusFilter (adjust to the actual repository method used)
        when(prescriptionRepository.findByStatus(PrescriptionStatus.SENT, pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the service method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("status", "SENT", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByStatus(PrescriptionStatus.SENT, pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithAllFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findAllContaining("searchTerm", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("all", "searchTerm", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findAllContaining("searchTerm", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithPrescriptionIdFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findByPrescriptionIdContainingIgnoreCase("123", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("prescriptionId", "123", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByPrescriptionIdContainingIgnoreCase("123", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithPatientIdFilter() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findByPatient_PatientIdContainingIgnoreCase("456", pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions("patientId", "456", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findByPatient_PatientIdContainingIgnoreCase("456", pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithNullFilters() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findAll(pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method with null filters
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions(null, null, pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findAll(pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testGetPrescriptions_WithEmptyFilters() {
        // Mock data
        Prescription prescription = new Prescription();
        Page<Prescription> prescriptionPage = new PageImpl<>(List.of(prescription));
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        // Mock repository and mapper
        when(prescriptionRepository.findAll(pageable)).thenReturn(prescriptionPage);
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

        // Call the method with empty filters
        Page<PrescriptionDTO> result = prescriptionService.getPrescriptions(" ", " ", pageable);

        // Assertions
        assertEquals(1, result.getTotalElements());
        verify(prescriptionRepository).findAll(pageable);
        verify(prescriptionMapper).toDTO(prescription);
    }

    @Test
    void testUpdatePrescriptionStatus() {
        // Arrange
        String prescriptionId = "123L";
        String newStatus = "CANCELLED";
        PrescriptionStatusEvent event = new PrescriptionStatusEvent();
        event.setPrescriptionId(prescriptionId);
        event.setStatus(PrescriptionStatus.CANCELLED);

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(prescriptionId);
        prescription.setStatus(PrescriptionStatus.PICKED_UP); // initial status

        // Mock the repository method
        when(prescriptionRepository.findByPrescriptionId(prescriptionId)).thenReturn(Optional.of(prescription));

        // Act
        prescriptionService.updatePrescriptionStatus(event);

        // Assert
        verify(prescriptionRepository).findByPrescriptionId(prescriptionId); // Verify findByPrescriptionId was called
        verify(prescriptionRepository).save(prescription); // Verify save was called
        assertEquals(newStatus, prescription.getStatus().name()); // Assert that the status was updated

    }

    @Test
    void testUpdatePrescriptionStatus_PrescriptionNotFound() {
        // Arrange
        String prescriptionId = "123L";
        PrescriptionStatusEvent event = new PrescriptionStatusEvent();
        event.setPrescriptionId(prescriptionId);
        event.setStatus(PrescriptionStatus.CANCELLED);

        // Mock the repository method to return empty (prescription not found)
        when(prescriptionRepository.findByPrescriptionId(prescriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> prescriptionService.updatePrescriptionStatus(event));
    }


}
