package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionMapperTest {

    private PrescriptionMapper prescriptionMapper;

    @BeforeEach
    void setUp() {
        prescriptionMapper = Mappers.getMapper(PrescriptionMapper.class);
    }


    @Test
    void toEntity_ShouldMapBasicFieldsAndIgnoreSpecifiedFields() {
        // Arrange
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId("f0234975"); // Ensure this matches as a String
        dto.setInstructions("Take twice daily");
        dto.setStatus(PrescriptionStatus.READY_FOR_PICKUP);
        dto.setIssueDate("2025-01-04");
        dto.setDosage("10mg");
        dto.setQuantity(30);

        // Act
        Prescription result = prescriptionMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Take twice daily", result.getInstructions());
        assertEquals(PrescriptionStatus.READY_FOR_PICKUP, result.getStatus());
        assertEquals("2025-01-04", result.getIssueDate());
        assertEquals("10mg", result.getDosage());
        assertEquals(30, result.getQuantity());

        // Verify ignored fields are null
        assertNull(result.getId());
        assertNull(result.getMedication());
        assertNull(result.getDoctor());
        assertNull(result.getPharmacy());
        assertNull(result.getPatient());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }
    

    @Test
    void toDTO_ShouldHandleEmptyObjects() {
        // Arrange
        Doctor doctor = new Doctor(null, null, null, null);
        Patient patient = new Patient(null, null, null, null, null, null, null, null);
        Medication medication = new Medication(null, null);
        Pharmacy pharmacy = new Pharmacy(null, null, null, null);

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setMedication(medication);
        prescription.setPharmacy(pharmacy);

        // Act
        PrescriptionDTO result = prescriptionMapper.toDTO(prescription);

        // Assert
        assertNotNull(result);
        assertAll(
                () -> assertNull(result.getPrescriptionId()),
                () -> assertNull(result.getPatientId()),
                () -> assertNull(result.getPatientName()),
                () -> assertNull(result.getMedicationId()),
                () -> assertNull(result.getMedicationName()),
                () -> assertNull(result.getMedicationCode()),
                () -> assertNull(result.getDoctorId()),
                () -> assertNull(result.getDoctorName()),
                () -> assertNull(result.getPharmacyId()),
                () -> assertNull(result.getPharmacyName())
        );
    }
}
