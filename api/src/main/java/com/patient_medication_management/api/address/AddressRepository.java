package com.patient_medication_management.api.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByStreet1AndStreet2AndCityAndStateAndZipCode(String street1, String street2, String city, String state, String zipCode);
}
