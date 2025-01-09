package com.patient_medication_management.api.medication;

import static org.junit.jupiter.api.Assertions.*;

import com.patient_medication_management.api.medication.Medication;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MedicationTest {

    @Test
    void testMedicationConstructor() {
        // Arrange
        String medicationName = "Aspirin";
        String medicationCode = "ASP123";

        // Act
        Medication medication = new Medication(medicationName, medicationCode);

        // Assert
        assertNotNull(medication);
        assertEquals(medicationName, medication.getMedicationName());
        assertEquals(medicationCode, medication.getMedicationCode());
    }

    @Test
    void testMedicationSettersAndGetters() {
        // Arrange
        Medication medication = new Medication();

        // Act
        medication.setMedicationName("Ibuprofen");
        medication.setMedicationCode("IBU456");

        // Assert
        assertEquals("Ibuprofen", medication.getMedicationName());
        assertEquals("IBU456", medication.getMedicationCode());
    }

    @Test
    void testMedicationTimestamps() {
        // Arrange
        Medication medication = new Medication();
        LocalDateTime beforeSave = LocalDateTime.now();

        // Act
        // Normally, timestamps are set automatically by Hibernate during persistence operations,
        // but for testing, let's just manually set them to ensure they exist.
        medication.setCreatedAt(beforeSave);
        medication.setUpdatedAt(beforeSave);

        // Assert
        assertNotNull(medication.getCreatedAt());
        assertNotNull(medication.getUpdatedAt());
        assertTrue(medication.getCreatedAt().isAfter(beforeSave.minusSeconds(1)));
        assertTrue(medication.getUpdatedAt().isAfter(beforeSave.minusSeconds(1)));
    }

    @Test
    void testMedicationOneToManyRelationship() {
        // Arrange
        Medication medication = new Medication("Paracetamol", "PAR789");

        // Act
        // This would typically be validated via repository or when the entity is persisted.
        // In unit tests, you may not need to actually persist this unless testing repository behavior.
        assertNotNull(medication.getPrescriptions()); // Default is an empty list.
        assertTrue(medication.getPrescriptions().isEmpty());
    }
}
