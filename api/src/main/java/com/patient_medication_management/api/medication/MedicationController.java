package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.dto.responses.MedicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    public ResponseEntity<MedicationDTO> createMedication(@RequestBody MedicationDTO medicationDTO) {
        MedicationDTO createdMedication = medicationService.createMedication(medicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedication);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<MedicationDTO> getMedicationByName(@PathVariable String medicationName) {
        MedicationDTO medication = medicationService.getMedicationByName(medicationName);
        return ResponseEntity.ok(medication);
    }

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getMedications() {
        return ResponseEntity.ok(medicationService.getMedications());
    }
}
