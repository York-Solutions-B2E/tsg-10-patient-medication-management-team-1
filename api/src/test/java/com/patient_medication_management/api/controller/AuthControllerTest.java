package com.patient_medication_management.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.patient_medication_management.api.doctor.DoctorService;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// static imports for MockMvc testing methods
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testGetOrCreateDoctorUser() throws Exception {
        OAuth2User user = Mockito.mock(OAuth2User.class);
        when(user.getAttribute("sub")).thenReturn("123");
        when(user.getAttribute("given_name")).thenReturn("John");
        when(user.getAttribute("family_name")).thenReturn("Doe");
        when(user.getAttribute("email")).thenReturn("email@email.com");
        when(user.getAttribute("phone_number")).thenReturn("1234567890");
        DoctorDTO mockDoctorDTO = new DoctorDTO();
        mockDoctorDTO.setId(1L);
        mockDoctorDTO.setOktaId("123");
        mockDoctorDTO.setFirstName("John");
        mockDoctorDTO.setLastName("Doe");
        mockDoctorDTO.setEmail("email@email.com");
        mockDoctorDTO.setPhone("1234567890");
        when(doctorService.getOrCreateDoctorUser(any(OAuth2User))).thenReturn(mockDoctorDTO);

        ResponseEntity<DoctorDTO> response = authController.getOrCreateDoctorUser(user);

        assertEquals(mockDoctorDTO, response.getBody());
    }

}
