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

    Page<Patient> findByIdContainingIgnoreCase(String id, Pageable pageable);

    Page<Patient> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    Page<Patient> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Page<Patient> findByDobContaining(String dob, Pageable pageable);

    Page<Patient> findByGenderContainingIgnoreCase(String gender, Pageable pageable);

    Page<Patient> findByPhoneContaining(String phone, Pageable pageable);

    Page<Patient> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    // address i a separate table that is linked to the patient table





}
