//
//package com.patient_medication_management.api.mappers;
//
//import com.patient_medication_management.api.dto.responses.PatientDTO;
//import com.patient_medication_management.api.enums.PatientGender;
//import com.patient_medication_management.api.patient.Patient;
//import com.patient_medication_management.api.prescription.Prescription;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class PatientMapperTest {
//
//    private PatientMapper patientMapper;
//
//    @BeforeEach
//    void setUp() {
//        patientMapper = Mappers.getMapper(PatientMapper.class);
//    }
//
//    @Test
//    void toDTO_ShouldMapAllFieldsCorrectly() {
//        // Arrange
//        Patient patient = new Patient();
//        patient.setPatientId("903453f8");
//        patient.setFirstName("John");
//        patient.setLastName("Doe");
//        patient.setEmail("john.doe@email.com");
//        patient.setPhone("123-456-7890");
//        patient.setDob("1990-01-01");
//        patient.setGender(PatientGender.MALE);
//
//        List<Prescription> prescriptions = new ArrayList<>();
//        prescriptions.add(new Prescription());
//        prescriptions.add(new Prescription());
//        patient.setPrescriptions(prescriptions);
//
//        // Act
//        PatientDTO result = patientMapper.mapToDTO(patient);
//
//        // Assert
//        assertAll(
//                () -> assertEquals("903453f8", result.getPatientId()),
//                () -> assertEquals("John", result.getFirstName()),
//                () -> assertEquals("Doe", result.getLastName()),
//                () -> assertEquals("john.doe@email.com", result.getEmail()),
//                () -> assertEquals("123-456-7890", result.getPhone()),
//                () -> assertEquals("1990-01-01", result.getDob()),
//                () -> assertEquals(PatientGender.MALE, result.getGender()),
//                () -> assertEquals(2, result.getPrescriptionCount())
//        );
//    }
//
//    @Test
//    void toDTO_ShouldHandleNullValues() {
//        // Arrange
//        Patient patient = new Patient();
//        patient.setPatientId("903453f8");
//
//        // Act
//        PatientDTO result = patientMapper.mapToDTO(patient);
//
//        // Assert
//        assertAll(
//                () -> assertNotNull(result),
//                () -> assertEquals("903453f8", result.getPatientId()),
//                () -> assertNull(result.getFirstName()),
//                () -> assertNull(result.getLastName()),
//                () -> assertNull(result.getEmail()),
//                () -> assertNull(result.getPhone()),
//                () -> assertNull(result.getDob()),
//                () -> assertNull(result.getGender()),
//                () -> assertEquals(0, result.getPrescriptionCount())
//        );
//    }
//
//}