package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.dob) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR (LOWER(p.gender) = LOWER(:searchTerm)) " + // Exact match for gender
            "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.id) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Patient> searchPatients(String searchTerm, Pageable pageable);



}
