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
    void toDTO_ShouldMapAllFieldsCorrectly() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Sarah");
        doctor.setLastName("Brown");

        Patient patient = new Patient();
        patient.setPatientId("7b07731a");
        patient.setFirstName("Jane");
        patient.setLastName("Doe");

        Medication medication = new Medication();
        medication.setId(2L);
        medication.setMedicationName("Ibuprofen");
        medication.setMedicationCode("IBU200");

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(3L);
        pharmacy.setName("CVS Pharmacy");

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("dd5eafc9");
        prescription.setInstructions("Take twice daily");
        prescription.setStatus(PrescriptionStatus.READY_FOR_PICKUP);
        prescription.setIssueDate("2025-01-04");
        prescription.setDosage("10mg");
        prescription.setQuantity(30);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setMedication(medication);
        prescription.setPharmacy(pharmacy);

        // Act
        PrescriptionDTO result = prescriptionMapper.toDTO(prescription);

        // Assert
        assertNotNull(result);
        assertEquals("dd5eafc9", result.getPrescriptionId());
        assertEquals("7b07731a", result.getPatientId());
        assertEquals("Jane Doe", result.getPatientName());
        assertEquals(2L, result.getMedicationId());
        assertEquals("Ibuprofen", result.getMedicationName());
        assertEquals("IBU200", result.getMedicationCode());
        assertEquals("Take twice daily", result.getInstructions());
        assertEquals(PrescriptionStatus.READY_FOR_PICKUP, result.getStatus());
        assertEquals("2025-01-04", result.getIssueDate());
        assertEquals("10mg", result.getDosage());
        assertEquals(30, result.getQuantity());
        assertEquals(1L, result.getDoctorId());
        assertEquals("Sarah Brown", result.getDoctorName());
        assertEquals(3L, result.getPharmacyId());
        assertEquals("CVS Pharmacy", result.getPharmacyName());
    }

    @Test
    void toEntity_ShouldMapBasicFieldsAndIgnoreSpecifiedFields() {
        // Arrange
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId("dd5eafc9");
        dto.setInstructions("Take twice daily");
        dto.setStatus(PrescriptionStatus.READY_FOR_PICKUP);
        dto.setIssueDate("2025-01-04");
        dto.setDosage("10mg");
        dto.setQuantity(30);
        // Set ignored fields
        dto.setDoctorId(1L);
        dto.setPatientId("P123");
        dto.setMedicationId(2L);
        dto.setPharmacyId(3L);

        // Act
        Prescription result = prescriptionMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals("dd5eafc9", result.getPrescriptionId());
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
    void toDTO_ShouldHandleNullValues() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("dd5eafc9");
        // Don't set any other fields - they should remain null

        // Act
        PrescriptionDTO result = prescriptionMapper.toDTO(prescription);

        // Assert
        assertNotNull(result);
        assertEquals("dd5eafc9", result.getPrescriptionId());
        assertAll(
                () -> assertNull(result.getPatientId()),
                () -> assertNull(result.getPatientName()),
                () -> assertNull(result.getMedicationId()),
                () -> assertNull(result.getMedicationName()),
                () -> assertNull(result.getMedicationCode()),
                () -> assertNull(result.getInstructions()),
                () -> assertNull(result.getStatus()),
                () -> assertNull(result.getIssueDate()),
                () -> assertNull(result.getDosage()),
                () -> assertNull(result.getQuantity()),
                () -> assertNull(result.getDoctorId()),
                () -> assertNull(result.getDoctorName()),
                () -> assertNull(result.getPharmacyId()),
                () -> assertNull(result.getPharmacyName())
        );
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
