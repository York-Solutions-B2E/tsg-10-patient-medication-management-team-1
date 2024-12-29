package com.patient_medication_management.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(("/**")) // Allow CORS for all endpoints
                .allowedOrigins("http://localhost:3000") // Only allow requests from React frontend running on port 3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("*") // Allow all types of headers in requests
                .allowCredentials(true); // Allows sending of cookies and authentication headers
    }
}
