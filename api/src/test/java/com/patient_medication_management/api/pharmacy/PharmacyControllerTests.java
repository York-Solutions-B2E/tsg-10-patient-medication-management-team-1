package com.patient_medication_management.api.pharmacy;

import com.patient_medication_management.api.dto.responses.PharmacyDTO;
import com.patient_medication_management.api.pharmacy.PharmacyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PharmacyControllerTest {

    @Mock
    private PharmacyService pharmacyService;

    @InjectMocks
    private PharmacyController pharmacyController;

    @Test
    void testGetPharmacies() throws Exception {
        // Arrange: Prepare mock response
        PharmacyDTO pharmacyDTO1 = new PharmacyDTO(1L, "Pharmacy 1", "123 Main St", "555-1234", "555-5678");
        PharmacyDTO pharmacyDTO2 = new PharmacyDTO(2L, "Pharmacy 2", "456 Oak St", "555-9876", "555-4321");
        List<PharmacyDTO> pharmacies = Arrays.asList(pharmacyDTO1, pharmacyDTO2);

        // Mock service to return the pharmacy list
        when(pharmacyService.getPharmacies()).thenReturn(pharmacies);
        ResponseEntity response = pharmacyController.getPharmacies();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(pharmacies, response.getBody());
    }
}
