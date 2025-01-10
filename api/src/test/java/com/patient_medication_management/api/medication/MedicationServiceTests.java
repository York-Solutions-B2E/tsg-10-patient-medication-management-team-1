package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import com.patient_medication_management.api.mappers.MedicationMapper;
import com.patient_medication_management.api.medication.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MedicationServiceTests {

    private MedicationService medicationService;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private MedicationMapper medicationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medicationService = new MedicationService(medicationRepository, medicationMapper);
    }

    @Test
    void testGetMedication() {
        Medication medication = new Medication();
        medication.setId(1L);
        medication.setMedicationName("test");

        Medication medication2 = new Medication();
        medication2.setId(2L);
        medication2.setMedicationName("test2");

        List<Medication> medications = Arrays.asList(medication, medication2);
        when(medicationRepository.findAll()).thenReturn(medications);

        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setId(1L);
        medicationDTO.setMedicationName("test");
        MedicationDTO medicationDTO2 = new MedicationDTO();
        medicationDTO2.setId(2L);
        medicationDTO2.setMedicationName("test2");

        when(medicationMapper.mapToDTOs(medications)).thenReturn(Arrays.asList(medicationDTO, medicationDTO2));

        List<MedicationDTO> result = medicationService.getMedications();

        assertNotNull(result);
        assertEquals("test", result.get(0).getMedicationName());
        assertEquals("test2", result.get(1).getMedicationName());

        verify(medicationRepository).findAll();
        verify(medicationMapper).mapToDTOs(medications);
    }
}
