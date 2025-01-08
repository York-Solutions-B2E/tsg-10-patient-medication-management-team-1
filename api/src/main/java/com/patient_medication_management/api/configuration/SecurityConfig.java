package com.patient_medication_management.api.configuration;//package com.patient_medication_management.api.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
//
//// This class is responsible for configuring Spring Security
//@Configuration
//public class SecurityConfig {
//
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/", "/index.html", "/static/**",
//                        "/*.ico", "/*.json", "/*.png", "/login/oauth2/code/okta",
//                        "/api/logout", "/error")
//                .permitAll()
//                .anyRequest().authenticated());
//        http.csrf((csrf) -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                // permits public access for POST, PUT, DELETE requests
//        );
//        http.addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
//        http.oauth2Login(oauth2 -> oauth2
//                .defaultSuccessUrl("/", true));
//
//        return http.build();
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests
                );
        return http.build();
    }
}