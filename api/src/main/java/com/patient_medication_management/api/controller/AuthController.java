package com.patient_medication_management.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.patient_medication_management.api.doctor.DoctorService;
import com.patient_medication_management.api.dto.responses.DoctorDTO;

import java.util.HashMap;
import java.util.Map;

/*
 * This Controller bridges our React frontend with Okta Authentication,
 * managing the authentication flow and user session
 */
@Controller
public class AuthController {

    private final ClientRegistration registration;
    private final DoctorService doctorService;

    @Autowired
    public AuthController(ClientRegistrationRepository registrations, DoctorService doctorService) {
        this.registration = registrations.findByRegistrationId("okta");
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public ResponseEntity<?> redirectToFrontend(HttpServletResponse response) {
        response.setHeader("Location", "http://localhost:3000/");
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @PostMapping("/api/user")
    public ResponseEntity<DoctorDTO> getOrCreateDoctorUser(@AuthenticationPrincipal OAuth2User user)
            throws Exception {
        return ResponseEntity.ok(doctorService.getOrCreateDoctorUser(user));
    }

    // Provide user data to the frontend - Needed to manage role-based access for react-router-dom routes
    // Login route : automatically created by OAuth2 in the Security Config /login/oauth2/code/okta
    // Logout route
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
            @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {

        // Build the logout details (end session endpoint and id token) to send to the client
        Map<String, String> logoutDetails = new HashMap<>();
        String logoutUrl = this.registration.getProviderDetails().getConfigurationMetadata().get("end_session_endpoint").toString();
        logoutDetails.put("logoutUrl", logoutUrl);
        logoutDetails.put("idToken", idToken.getTokenValue());

        // Debugging
        System.out.println("LogoutDetails, logoutURL: " + logoutUrl);
        System.out.println("LogoutDetails, idToken: " + idToken.getTokenValue());

        // Clear session
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        return ResponseEntity.ok().body(logoutDetails);
    }
}
