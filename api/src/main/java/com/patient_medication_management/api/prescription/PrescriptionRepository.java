package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.enums.PrescriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Search by Prescription ID
    Page<Prescription> findByPrescriptionIdContainingIgnoreCase(String prescriptionId, Pageable pageable);

    // Search by Patient ID
    Page<Prescription> findByPatient_PatientIdContainingIgnoreCase(String patientId, Pageable pageable); // CHANGED TO "PatientId"

    @Query("SELECT p FROM Prescription p " +
            "JOIN p.patient patient " +
            "WHERE LOWER(patient.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(patient.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Prescription> findByPatientNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Prescription p " +
            "JOIN p.medication medication " +
            "WHERE LOWER(medication.medicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Prescription> findByMedicationNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Search by Instructions
    Page<Prescription> findByInstructionsContainingIgnoreCase(String instruction, Pageable pageable);

    // Search by Status
    Page<Prescription> findByStatus(PrescriptionStatus status, Pageable pageable);

    @Query("SELECT p FROM Prescription p " +
            "LEFT JOIN p.patient patient " +
            "LEFT JOIN p.medication medication " +
            "LEFT JOIN p.doctor doctor " +
            "LEFT JOIN p.pharmacy pharmacy " +
            "WHERE LOWER(p.prescriptionId) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(patient.patientId) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " + // CHANGED TO "patient.patientId"
            "OR LOWER(patient.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(patient.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(medication.medicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(medication.medicationCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(doctor.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(doctor.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(pharmacy.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.instructions) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.status) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Prescription> findAllContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Prescription> findByPrescriptionId(String prescriptionId);

}
