package com.patient_medication_management.api.controller;

import com.patient_medication_management.api.dto.responses.DoctorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import com.patient_medication_management.api.doctor.DoctorService;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

// static imports for MockMvc testing methods
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private ClientRegistrationRepository registrations;

    @Mock
    private DoctorService doctorService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRedirectToFrontend() {
        ResponseEntity<?> response = authController.redirectToFrontend(httpServletResponse);

        // Verify response header and status code
        verify(httpServletResponse).setHeader("Location", "http://localhost:3000/");
        assertEquals(HttpStatusCode.valueOf(302), response.getStatusCode()); // 302 is HttpStatus.FOUND
    }

    @Test
    void testGetOrCreateDoctorUser() throws Exception {
        OAuth2User mockUser = mock(OAuth2User.class);
        DoctorDTO doctorDTO = new DoctorDTO(); // Assuming a no-args constructor for DTO

        // Stub DoctorService for this test
        when(doctorService.getOrCreateDoctorUser(mockUser)).thenReturn(doctorDTO);

        ResponseEntity<DoctorDTO> response = authController.getOrCreateDoctorUser(mockUser);

        // Verify response content
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(doctorDTO, response.getBody());
    }

    @Test
    void testLogout() {
        // Mock objects
        OidcIdToken idToken = mock(OidcIdToken.class);
        ClientRegistration mockRegistration = mock(ClientRegistration.class);
        ClientRegistration.ProviderDetails providerDetails = mock(ClientRegistration.ProviderDetails.class);

        // Stubbing chain
        when(registrations.findByRegistrationId("okta")).thenReturn(mockRegistration);
        when(mockRegistration.getProviderDetails()).thenReturn(providerDetails);
        when(providerDetails.getConfigurationMetadata())
                .thenReturn(Map.of("end_session_endpoint", "http://logout.endpoint"));
        when(idToken.getTokenValue()).thenReturn("mock-token");

        // Call the logout method
        ResponseEntity<?> response = authController.logout(httpServletRequest, idToken);

        // Verify interactions and response content
        verify(httpServletRequest, times(1)).getSession(false);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(
                Map.of(
                        "logoutUrl", "http://logout.endpoint",
                        "idToken", "mock-token"
                ),
                response.getBody()
        );
    }
}


