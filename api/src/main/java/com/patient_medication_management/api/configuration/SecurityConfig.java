package com.patient_medication_management.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

// This class is responsible for configuring Spring Security
@Configuration
public class SecurityConfig {

    //    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests((auth) -> auth
//                // For testing purpose: Add /api/patients/** and /api/prescriptions/**" to the list of publicly accessible endpoints
//                .requestMatchers("/", "/index.html", "/static/**",
//                        "/*.ico", "/*.json", "/*.png", "/login/oauth2/code/okta",
//                        "/api/logout", "error")
//                .permitAll()
//                .anyRequest().authenticated());
//        http.csrf((csrf) -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                //.ignoringRequestMatchers("/api/patients/**")  //permits public access for Testing
//                // permits public access for POST, PUT, DELETE requests
//        );
//        http.addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
//        http.oauth2Login(oauth2 -> oauth2
//                .defaultSuccessUrl("/", true));
//
//        return http.build();
//    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/static/**", "/*.ico", "/*.json", "/*.png",
                                "/login/oauth2/code/okta", "/api/logout", "/api/patients/**", "/api/prescriptions/**", "/error").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true));

        return http.build();
    }
}
