package com.patient_medication_management.api.patient;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@WebMvcTest(PatientController.class)
public class PatientControllerTests {

}
