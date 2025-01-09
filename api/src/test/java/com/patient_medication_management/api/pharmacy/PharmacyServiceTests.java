package com.patient_medication_management.api.pharmacy;

import com.patient_medication_management.api.dto.responses.PharmacyDTO;
import com.patient_medication_management.api.mappers.PharmacyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceTests {

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private PharmacyMapper pharmacyMapper;

    @InjectMocks
    private PharmacyService pharmacyService;

    @Test
    void testGetPharmacies() {
        // Arrange: Prepare mock data
        Pharmacy pharmacy1 = new Pharmacy(1L, "Pharmacy 1", "123 Main St", "555-1234", "555-5678", null);
        Pharmacy pharmacy2 = new Pharmacy(2L, "Pharmacy 2", "456 Oak St", "555-9876", "555-4321", null);
        List<Pharmacy> pharmacies = Arrays.asList(pharmacy1, pharmacy2);

        // Mock the repository to return the pharmacy list
        when(pharmacyRepository.findAll()).thenReturn(pharmacies);

        // Mock the mapper to return corresponding DTOs
        PharmacyDTO pharmacyDTO1 = new PharmacyDTO(1L, "Pharmacy 1", "123 Main St", "555-1234", "555-5678");
        PharmacyDTO pharmacyDTO2 = new PharmacyDTO(2L, "Pharmacy 2", "456 Oak St", "555-9876", "555-4321");
        List<PharmacyDTO> pharmacyDTOs = Arrays.asList(pharmacyDTO1, pharmacyDTO2);
        when(pharmacyMapper.mapToDTOs(pharmacies)).thenReturn(pharmacyDTOs);

        // Act: Call the service method
        List<PharmacyDTO> result = pharmacyService.getPharmacies();

        // Assert: Verify the result
        assertEquals(2, result.size());  // Assert that the result contains 2 items
        assertEquals("Pharmacy 1", result.get(0).getName());  // Verify the name of the first pharmacy DTO
        assertEquals("Pharmacy 2", result.get(1).getName());  // Verify the name of the second pharmacy DTO

        // Verify that the repository and mapper methods were called
        verify(pharmacyRepository, times(1)).findAll();
        verify(pharmacyMapper, times(1)).mapToDTOs(pharmacies);
    }
}
