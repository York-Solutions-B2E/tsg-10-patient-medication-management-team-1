package com.patient_medication_management.api.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByPatientIdContainingIgnoreCase(String patientId, Pageable pageable);

    Page<Patient> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    Page<Patient> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Page<Patient> findByDobContaining(String dob, Pageable pageable);

    Page<Patient> findByGenderContainingIgnoreCase(String gender, Pageable pageable);

    Page<Patient> findByPhoneContaining(String phone, Pageable pageable);

    Page<Patient> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN p.address a " +  // Join Patient with Address
            "Where p.patientId LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.firstName LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.lastName LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.dob LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.gender LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.phone LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR p.email LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.street1 LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.street2 LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.city LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.state LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.zipCode LIKE LOWER(CONCAT(%:searchTerm%))")
    Page<Patient> findAllContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN p.address a " +  // Join Patient with Pharmacy
            "Where a.street1 LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.street2 LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.city LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.state LIKE LOWER(CONCAT(%:searchTerm%)) " +
            "OR a.zipCode LIKE LOWER(CONCAT(%:searchTerm%))")
    Page<Patient> findByAddressContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    boolean existsByPatientId(String patientId);

    void deleteByPatientId(String patientId);

    Optional<Patient> findByPatientId(String patientId);

    // address i a separate table that is linked to the patient table


}
