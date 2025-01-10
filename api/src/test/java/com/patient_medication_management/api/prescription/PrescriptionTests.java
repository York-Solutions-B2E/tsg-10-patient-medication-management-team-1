package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionTest {

    @Test
    void testPrescriptionConstructor() {
        // Arrange: Create necessary objects for dependencies
        Medication medication = new Medication();
        medication.setId(1L);
        medication.setMedicationName("Med1");
        medication.setMedicationCode("xxx");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Smith");

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        pharmacy.setName("Pharmacy1");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        // Act: Create a Prescription instance using the constructor
        Prescription prescription = new Prescription("Take once a day", "RX1234", PrescriptionStatus.SENT, "2025-01-01", "500mg", 30, medication, doctor, pharmacy, patient);

        // Assert: Check that the fields are set correctly
        assertNotNull(prescription);
        assertEquals("RX1234", prescription.getPrescriptionId());
        assertEquals("Take once a day", prescription.getInstructions());
        assertEquals(PrescriptionStatus.SENT, prescription.getStatus());
        assertEquals("2025-01-01", prescription.getIssueDate());
        assertEquals("500mg", prescription.getDosage());
        assertEquals(30, prescription.getQuantity());
        assertEquals(medication, prescription.getMedication());
        assertEquals(doctor, prescription.getDoctor());
        assertEquals(pharmacy, prescription.getPharmacy());
        assertEquals(patient, prescription.getPatient());
    }

    @Test
    void testPrescriptionBuilder() {
        // Arrange: Create necessary objects for dependencies
        Medication medication = new Medication();
        medication.setId(1L);
        medication.setMedicationName("Med1");
        medication.setMedicationCode("xxx");

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Smith");

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        pharmacy.setName("Pharmacy1");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        // Act: Create a Prescription instance using the builder
        Prescription prescription = Prescription.builder()
                .prescriptionId("RX1234")
                .instructions("Take once a day")
                .status(PrescriptionStatus.SENT)
                .issueDate("2025-01-01")
                .dosage("500mg")
                .quantity(30)
                .medication(medication)
                .doctor(doctor)
                .pharmacy(pharmacy)
                .patient(patient)
                .build();

        // Assert: Check that the fields are set correctly
        assertNotNull(prescription);
        assertEquals("RX1234", prescription.getPrescriptionId());
        assertEquals("Take once a day", prescription.getInstructions());
        assertEquals(PrescriptionStatus.SENT, prescription.getStatus());
        assertEquals("2025-01-01", prescription.getIssueDate());
        assertEquals("500mg", prescription.getDosage());
        assertEquals(30, prescription.getQuantity());
        assertEquals(medication, prescription.getMedication());
        assertEquals(doctor, prescription.getDoctor());
        assertEquals(pharmacy, prescription.getPharmacy());
        assertEquals(patient, prescription.getPatient());
    }
}
