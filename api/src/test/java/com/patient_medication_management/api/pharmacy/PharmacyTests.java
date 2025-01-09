package com.patient_medication_management.api.pharmacy;

import static org.junit.jupiter.api.Assertions.*;

import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class PharmacyTest {

    @Test
    void testPharmacyConstructor() {
        // Arrange
        String name = "City Pharmacy";
        String address = "123 Main St, Springfield";
        String phone = "555-1234";
        String fax = "555-5678";

        // Act
        Pharmacy pharmacy = new Pharmacy(name, address, phone, fax);

        // Assert
        assertNotNull(pharmacy);
        assertEquals(name, pharmacy.getName());
        assertEquals(address, pharmacy.getAddress());
        assertEquals(phone, pharmacy.getPhone());
        assertEquals(fax, pharmacy.getFax());
    }

    @Test
    void testPharmacySettersAndGetters() {
        // Arrange
        Pharmacy pharmacy = new Pharmacy();

        // Act
        pharmacy.setName("Downtown Pharmacy");
        pharmacy.setAddress("456 Oak St, Springfield");
        pharmacy.setPhone("555-9876");
        pharmacy.setFax("555-4321");

        // Assert
        assertEquals("Downtown Pharmacy", pharmacy.getName());
        assertEquals("456 Oak St, Springfield", pharmacy.getAddress());
        assertEquals("555-9876", pharmacy.getPhone());
        assertEquals("555-4321", pharmacy.getFax());
    }

    @Test
    void testPharmacyOneToManyRelationship() {
        // Arrange
        Pharmacy pharmacy = new Pharmacy("Central Pharmacy", "789 Birch St, Springfield", "555-5555", "555-9999");

        // Act
        // Check that the prescriptions list is initialized as empty (default behavior)
        assertNotNull(pharmacy.getPrescriptions());
        assertTrue(pharmacy.getPrescriptions().isEmpty());

        // Optional: You can mock prescriptions and check further
        Prescription prescription = new Prescription();
        prescription.setPharmacy(pharmacy);
        pharmacy.setPrescriptions(Collections.singletonList(prescription));

        // Assert
        assertEquals(1, pharmacy.getPrescriptions().size());
        assertEquals(pharmacy, pharmacy.getPrescriptions().get(0).getPharmacy());
    }
}
