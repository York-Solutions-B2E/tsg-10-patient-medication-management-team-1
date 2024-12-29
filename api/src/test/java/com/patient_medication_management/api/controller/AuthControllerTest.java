package com.patient_medication_management.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// static imports for MockMvc testing methods
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockitoBean
    private ClientRegistration mockClientRegistration;

    @MockitoBean
    private OAuth2User oauth2User;

    @MockitoBean
    private OAuth2AuthorizedClient authorizedClient;

    @MockitoBean
    private OidcIdToken mockIdToken;

    private AuthController authController;

    @BeforeEach
    void setUpMocks() {
        // Mock ClientRegistration
        when(clientRegistrationRepository.findByRegistrationId("okta"))
                .thenReturn(mockClientRegistration);

        ClientRegistration.ProviderDetails mockProviderDetails = Mockito.mock(ClientRegistration.ProviderDetails.class);
        Map<String, Object> configurationMetadata = new HashMap<>();
        configurationMetadata.put("end_session_endpoint", "https://test.okta.com/logout");

        when(mockProviderDetails.getConfigurationMetadata()).thenReturn(configurationMetadata);
        when(mockClientRegistration.getProviderDetails()).thenReturn(mockProviderDetails);

        // Initialize MockMvc with webAppContextSetup to include Spring Security
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()) // Apply Spring Security filter chain
                .build();
    }

    @Test
    void redirectToFrontend_ShouldRedirectToLocalhost() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        // Perform a GET request to the root URL "/"
        // Assertions to verify the response
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:3000/"));
    }

    @Test
    void getUser_WhenUserIsNull_ShouldReturnEmptyBody() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Assertions to verify the response
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getUser_WhenUserExists_ShouldReturnUserAttributes() throws Exception {
        // Mock OAuth2 user attributes
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "Test User");
        attributes.put("email", "test@example.com");

        // Configure the mocked oauth2User
        when(oauth2User.getAttributes()).thenReturn(attributes);
        when(oauth2User.getName()).thenReturn("test-principal"); // Set principal name via getName()

        // MockMvc setup with Spring Security
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        // Assertions to verify the response
        mockMvc.perform(get("/api/user")
                        .with(oauth2Login().oauth2User(oauth2User)))  // OAuth2User contains the mocked principal
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User")) // Verify "name" in response
                .andExpect(jsonPath("$.email").value("test@example.com")); // Verify "email" in response
    }

    @Test
    void oauthUserInfo_ShouldReturnFormattedUserInfo() throws Exception {
        // Mock OAuth2 user details
        when(oauth2User.getName()).thenReturn("Test User");
        when(oauth2User.getAuthorities()).thenReturn(Collections.emptyList());

        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("email", "test@example.com");
        attributes.put("role", "user");
        when(oauth2User.getAttributes()).thenReturn(attributes);

        // Mock ClientRegistration
        when(mockClientRegistration.getClientName()).thenReturn("Okta");

        // Configure MockMvc with Spring Security
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        // Perform the test request with oauth2Client configuration
        String responseContent = mockMvc.perform(get("/api/oauthinfo")
                        .with(oauth2Login().oauth2User(oauth2User))
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Client()
                                .clientRegistration(mockClientRegistration)
                                .principalName("test-user")))
                .andReturn()
                .getResponse()
                .getContentAsString();
        //System.out.println("Response Content : " + responseContent);

        // Expected response
        String expectedResponse = "User Name: Test User<br/>" +
                "User Authorities: []<br/>" +
                "Client Name: Okta<br/>" +
                "User Attributes: <br/><div style='padding-left:20px'>" +
                "<div>email:&nbsptest@example.com</div>" +
                "<div>role:&nbspuser</div>" +
                "</div>";

        // Assertions
        assertEquals(expectedResponse, responseContent);
    }

}
