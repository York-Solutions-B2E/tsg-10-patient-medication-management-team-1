package com.patient_medication_management.api.pharmacy;

import com.patient_medication_management.api.dto.responses.PharmacyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @GetMapping
    public ResponseEntity<List<PharmacyDTO>> getPharmacies() {
        return ResponseEntity.ok(pharmacyService.getPharmacies());
    }
}
