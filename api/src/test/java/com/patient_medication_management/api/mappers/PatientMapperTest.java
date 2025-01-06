
package com.patient_medication_management.api.mappers;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.dto.responses.AddressDTO;
import com.patient_medication_management.api.dto.responses.PatientDTO;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.prescription.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientMapperTest {

    private PatientMapper patientMapper;
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() throws Exception {
        // Create a mock for AddressMapper
        addressMapper = Mockito.mock(AddressMapper.class);

        // Use Mappers.getMapper to get the PatientMapper instance
        patientMapper = Mappers.getMapper(PatientMapper.class);

        // Use reflection to inject the mock AddressMapper into PatientMapperImpl
        Field addressMapperField = patientMapper.getClass().getDeclaredField("addressMapper");
        addressMapperField.setAccessible(true); // Make the private field accessible
        addressMapperField.set(patientMapper, addressMapper);
    }

    @Test
    void toDTO_ShouldMapAllFieldsCorrectly() {
        // Arrange
        Address address = new Address();
        address.setStreet1("123 Main St");
        address.setStreet2("Apt 4B");
        address.setCity("Boston");
        address.setState("MA");
        address.setZipCode("02108");

        Patient patient = new Patient();
        patient.setPatientId("903453f8");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john.doe@email.com");
        patient.setPhone("123-456-7890");
        patient.setDob("1990-01-01");
        patient.setGender(PatientGender.MALE);
        patient.setAddress(address);

        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());
        prescriptions.add(new Prescription());
        patient.setPrescriptions(prescriptions);

        AddressDTO mockAddressDTO = new AddressDTO();
        mockAddressDTO.setStreet1("123 Main St");
        mockAddressDTO.setStreet2("Apt 4B");
        mockAddressDTO.setCity("Boston");
        mockAddressDTO.setState("MA");
        mockAddressDTO.setZipCode("02108");

        Mockito.when(addressMapper.mapToDTO(address)).thenReturn(mockAddressDTO);

        // Act
        PatientDTO result = patientMapper.mapToDTO(patient);

        // Assert
        assertAll(
                () -> assertEquals("903453f8", result.getPatientId()),
                () -> assertEquals("John", result.getFirstName()),
                () -> assertEquals("Doe", result.getLastName()),
                () -> assertEquals("john.doe@email.com", result.getEmail()),
                () -> assertEquals("123-456-7890", result.getPhone()),
                () -> assertEquals("1990-01-01", result.getDob()),
                () -> assertEquals(PatientGender.MALE, result.getGender()),
                () -> assertEquals(2, result.getPrescriptionCount())
        );

        // Assert address mapping
        assertNotNull(result.getAddress());
        assertAll(
                () -> assertEquals("123 Main St", result.getAddress().getStreet1()),
                () -> assertEquals("Apt 4B", result.getAddress().getStreet2()),
                () -> assertEquals("Boston", result.getAddress().getCity()),
                () -> assertEquals("MA", result.getAddress().getState()),
                () -> assertEquals("02108", result.getAddress().getZipCode())
        );
    }

    @Test
    void toDTO_ShouldHandleNullValues() {
        // Arrange
        Patient patient = new Patient();
        patient.setPatientId("903453f8");

        Mockito.when(addressMapper.mapToDTO(null)).thenReturn(null);

        // Act
        PatientDTO result = patientMapper.mapToDTO(patient);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("903453f8", result.getPatientId()),
                () -> assertNull(result.getFirstName()),
                () -> assertNull(result.getLastName()),
                () -> assertNull(result.getEmail()),
                () -> assertNull(result.getPhone()),
                () -> assertNull(result.getDob()),
                () -> assertNull(result.getGender()),
                () -> assertNull(result.getAddress()),
                () -> assertEquals(0, result.getPrescriptionCount())
        );
    }
}