package com.patient_medication_management.api.controller;

import jakarta.servlet.http.HttpServletRequest;
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

import java.util.HashMap;
import java.util.Map;

/*
 * This Controller bridges our React frontend with Okta Authentication,
 * managing the authentication flow and user session
 */
@Controller
public class AuthController {

    private final ClientRegistration registration;

    public AuthController(ClientRegistrationRepository registration) {
        this.registration = registration.findByRegistrationId("okta");
    }

    @GetMapping("/")
    public String redirectToFrontend() {
        return "redirect:http://localhost:3000/";
    }

    // Provide user data to the frontend - Needed to manage role-based access for react-router-dom routes
    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.ok(user.getAttributes());
    }

    @RequestMapping("/api/oauthinfo")
    @ResponseBody
    public String oauthUserInfo(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                @AuthenticationPrincipal OAuth2User oauth2User) {
        return "User Name: " + oauth2User.getName() + "<br/>" +
                "User Authorities: " + oauth2User.getAuthorities() + "<br/>" +
                "Client Name: " + authorizedClient.getClientRegistration().getClientName() + "<br/>" +
                this.prettyPrintAttributes(oauth2User.getAttributes());
    }

    private String prettyPrintAttributes(Map<String, Object> attributes) {
        String acc = "User Attributes: <br/><div style='padding-left:20px'>";
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);
            acc += "<div>" + key + ":&nbsp" + value.toString() + "</div>";
        }
        return acc + "</div>";
    }

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
