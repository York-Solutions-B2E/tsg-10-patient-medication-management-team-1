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

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN p.address a " +  // Join Patient with Address
            "WHERE p.firstName LIKE %:searchTerm% " +
            "OR p.lastName LIKE %:searchTerm% " +
            "OR p.dob LIKE %:searchTerm% " +
            "OR p.gender LIKE %:searchTerm% " +
            "OR a.streetOne LIKE %:searchTerm% " +
            "OR a.streetTwo LIKE %:searchTerm% " +
            "OR a.city LIKE %:searchTerm% " +
            "OR a.state LIKE %:searchTerm% " +
            "OR a.zip LIKE %:searchTerm%")
    Page<Patient> findAllContaining(String filterName, Pageable pageable);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN p.pharmacy ph " +  // Join Patient with Pharmacy
            "WHERE ph.name LIKE %:searchTerm% " +
            "OR ph.phone LIKE %:searchTerm% " +
            "OR ph.email LIKE %:searchTerm% " +
            "OR ph.address.streetOne LIKE %:searchTerm% " +
            "OR ph.address.streetTwo LIKE %:searchTerm% " +
            "OR ph.address.city LIKE %:searchTerm% " +
            "OR ph.address.state LIKE %:searchTerm% " +
            "OR ph.address.zip LIKE %:searchTerm%")
    Page<Patient> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN p.pharmacy ph " +  // Join Patient with Pharmacy
            "WHERE ph.name LIKE %:searchTerm% " +
            "OR ph.phone LIKE %:searchTerm% " +
            "OR ph.email LIKE %:searchTerm% " +
            "OR ph.address.streetOne LIKE %:searchTerm% " +
            "OR ph.address.streetTwo LIKE %:searchTerm% " +
            "OR ph.address.city LIKE %:searchTerm% " +
            "OR ph.address.state LIKE %:searchTerm% " +
            "OR ph.address.zip LIKE %:searchTerm%")
    Page<Patient> findByPharmacyContainingIgnoreCase(String pharmacy, Pageable pageable);

    // address i a separate table that is linked to the patient table





}
