package com.patient_medication_management.api.doctor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.patient_medication_management.api.dto.responses.DoctorDTO;
import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.doctor.DoctorRepository;
import com.patient_medication_management.api.doctor.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

class DoctorServiceTest {

    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private OAuth2User oAuth2User;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void testGetOrCreateDoctorUser_NewDoctor() {
        // Arrange
        String oktaId = "12345";
        when(oAuth2User.getAttribute("sub")).thenReturn(oktaId);
        when(oAuth2User.getAttribute("given_name")).thenReturn("John");
        when(oAuth2User.getAttribute("family_name")).thenReturn("Doe");
        when(oAuth2User.getAttribute("email")).thenReturn("john.doe@example.com");
        when(oAuth2User.getAttribute("phone_number")).thenReturn("1234567890");

        when(doctorRepository.findByOktaId(oktaId)).thenReturn(Optional.empty());
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor savedDoctor = invocation.getArgument(0);
            savedDoctor.setId(1L); // Mock ID assignment
            return savedDoctor;
        });

        // Act
        DoctorDTO result = doctorService.getOrCreateDoctorUser(oAuth2User);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getOktaId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhone());
        verify(doctorRepository).findByOktaId(oktaId);
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testGetOrCreateDoctorUser_ExistingDoctor() {
        // Arrange
        String oktaId = "12345";
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(1L);
        existingDoctor.setOktaId(oktaId);
        existingDoctor.setFirstName("Existing");
        existingDoctor.setLastName("Doctor");
        existingDoctor.setEmail("existing.doctor@example.com");
        existingDoctor.setPhone("9876543210");

        when(oAuth2User.getAttribute("sub")).thenReturn(oktaId);
        when(doctorRepository.findByOktaId(oktaId)).thenReturn(Optional.of(existingDoctor));

        // Act
        DoctorDTO result = doctorService.getOrCreateDoctorUser(oAuth2User);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getOktaId());
        assertEquals("Existing", result.getFirstName());
        assertEquals("Doctor", result.getLastName());
        assertEquals("existing.doctor@example.com", result.getEmail());
        assertEquals("9876543210", result.getPhone());
        verify(doctorRepository).findByOktaId(oktaId);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}
