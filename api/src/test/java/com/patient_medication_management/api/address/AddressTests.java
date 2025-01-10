package com.patient_medication_management.api.address;

import com.patient_medication_management.api.patient.Patient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTests {

    @Test
    void testAddressBuilder() {
        Address address = Address.builder()
                .street1("123 Main St")
                .street2("Apt 4B")
                .city("Springfield")
                .state("IL")
                .zipCode("62704")
                .build();

        assertEquals("123 Main St", address.getStreet1());
        assertEquals("Apt 4B", address.getStreet2());
        assertEquals("Springfield", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("62704", address.getZipCode());
        assertNull(address.getPatients());
    }

    @Test
    void testDefaultPatientsList() {
        Address address = new Address();
        assertNull(address.getPatients());
    }

    @Test
    void testAddPatient() {
        Address address = new Address();
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient();
        patients.add(patient);

        address.setPatients(patients);

        assertNotNull(address.getPatients());
        assertEquals(1, address.getPatients().size());
        assertTrue(address.getPatients().contains(patient));
    }

    @Test
    void testConstructorWithFields() {
        Address address = new Address("123 Main St", "Apt 4B", "Springfield", "IL", "62704");

        assertEquals("123 Main St", address.getStreet1());
        assertEquals("Apt 4B", address.getStreet2());
        assertEquals("Springfield", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("62704", address.getZipCode());
    }

    @Test
    void testSettersAndGetters() {
        Address address = new Address();
        address.setStreet1("456 Elm St");
        address.setCity("Chicago");
        address.setState("IL");
        address.setZipCode("60616");

        assertEquals("456 Elm St", address.getStreet1());
        assertEquals("Chicago", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("60616", address.getZipCode());
    }

    @Test
    void testPatientsRelationship() {
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();

        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);

        Address address = new Address();
        address.setPatients(patients);

        assertEquals(2, address.getPatients().size());
        assertTrue(address.getPatients().contains(patient1));
        assertTrue(address.getPatients().contains(patient2));
    }
}
