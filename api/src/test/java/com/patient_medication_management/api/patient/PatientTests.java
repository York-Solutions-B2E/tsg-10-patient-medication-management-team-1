package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTests {
    @Test
    void testPatientBuilder() {
        Address address = new Address();
        Patient patient = Patient.builder()
                .patientId("12345678")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("123-456-7890")
                .dob("1990-01-01")
                .gender(PatientGender.MALE)
                .address(address)
                .build();

        assertEquals("12345678", patient.getPatientId());
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("john.doe@example.com", patient.getEmail());
        assertEquals("123-456-7890", patient.getPhone());
        assertEquals("1990-01-01", patient.getDob());
        assertEquals(PatientGender.MALE, patient.getGender());
        assertEquals(address, patient.getAddress());
        assertTrue(patient.getPrescriptions().isEmpty());
    }

    @Test
    void testDefaultPrescriptionsList() {
        Patient patient = new Patient();
        assertNotNull(patient.getPrescriptions());
        assertTrue(patient.getPrescriptions().isEmpty());
    }

    @Test
    void testAddPrescription() {
        Patient patient = new Patient();
        Prescription prescription = new Prescription();
        patient.getPrescriptions().add(prescription);

        assertEquals(1, patient.getPrescriptions().size());
        assertTrue(patient.getPrescriptions().contains(prescription));
    }

    @Test
    void testConstructor() {
        Address address = new Address();
        Patient patient = new Patient(
                "12345678",
                "John",
                "Doe",
                "john.doe@example.com",
                "123-456-7890",
                "1990-01-01",
                PatientGender.MALE,
                address
        );

        assertEquals("12345678", patient.getPatientId());
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("john.doe@example.com", patient.getEmail());
        assertEquals("123-456-7890", patient.getPhone());
        assertEquals("1990-01-01", patient.getDob());
        assertEquals(PatientGender.MALE, patient.getGender());
        assertEquals(address, patient.getAddress());
    }

    @Test
    void testSettersAndGetters() {
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");

        assertEquals("Jane", patient.getFirstName());
        assertEquals("Smith", patient.getLastName());
    }
}
