package com.patient_medication_management.api.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    boolean existsByMedicationName(String medicationName);

    Optional<Medication> findByMedicationName(String medicationName);
}
