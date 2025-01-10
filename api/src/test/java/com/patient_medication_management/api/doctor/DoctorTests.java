package com.patient_medication_management.api.doctor;

import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void testDoctorNoArgsConstructor() {
        Doctor doctor = new Doctor();
        assertNotNull(doctor);
        assertNull(doctor.getFirstName());
        assertNull(doctor.getLastName());
        assertNull(doctor.getEmail());
        assertNull(doctor.getPhone());
        assertNull(doctor.getPrescriptions());
    }

    @Test
    void testDoctorAllArgsConstructor() {
        Doctor doctor = new Doctor(1L, "John", "Doe", "uniqueOktaId", "john.doe@example.com", "123-456-7890", null);

        assertEquals(1L, doctor.getId());
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals("uniqueOktaId", doctor.getOktaId());
        assertEquals("john.doe@example.com", doctor.getEmail());
        assertEquals("123-456-7890", doctor.getPhone());
        assertNull(doctor.getPrescriptions());
    }

    @Test
    void testDoctorPartialConstructor() {
        Doctor doctor = new Doctor("John", "Doe", "john.doe@example.com", "123-456-7890");

        assertNull(doctor.getId());
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertNull(doctor.getOktaId());
        assertEquals("john.doe@example.com", doctor.getEmail());
        assertEquals("123-456-7890", doctor.getPhone());
        assertNull(doctor.getPrescriptions());
    }

    @Test
    void testSettersAndGetters() {
        Doctor doctor = new Doctor();

        doctor.setId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setOktaId("uniqueOktaId");
        doctor.setEmail("john.doe@example.com");
        doctor.setPhone("123-456-7890");

        assertEquals(1L, doctor.getId());
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals("uniqueOktaId", doctor.getOktaId());
        assertEquals("john.doe@example.com", doctor.getEmail());
        assertEquals("123-456-7890", doctor.getPhone());
    }

    @Test
    void testAddPrescriptions() {
        Doctor doctor = new Doctor();
        List<Prescription> prescriptions = new ArrayList<>();

        Prescription prescription1 = new Prescription();
        Prescription prescription2 = new Prescription();

        prescriptions.add(prescription1);
        prescriptions.add(prescription2);

        doctor.setPrescriptions(prescriptions);

        assertNotNull(doctor.getPrescriptions());
        assertEquals(2, doctor.getPrescriptions().size());
        assertTrue(doctor.getPrescriptions().contains(prescription1));
        assertTrue(doctor.getPrescriptions().contains(prescription2));
    }

    @Test
    void testUniqueFields() {
        Doctor doctor = new Doctor();
        doctor.setOktaId("uniqueOktaId");
        doctor.setEmail("john.doe@example.com");

        assertEquals("uniqueOktaId", doctor.getOktaId());
        assertEquals("john.doe@example.com", doctor.getEmail());
    }
}
