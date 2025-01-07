package com.patient_medication_management.api.prescription;

import com.patient_medication_management.api.dto.responses.PrescriptionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(path = "api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDTO> createPrescription(@Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        PrescriptionDTO created = prescriptionService.createPrescription(prescriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<PrescriptionDTO>> getPrescriptions(
            @RequestParam(required = false, defaultValue = "none") String filterName,
            @RequestParam(required = false, defaultValue = "") String filterValue,
            Pageable pageable
    ) {
        return ResponseEntity.ok(prescriptionService.getPrescriptions(filterName, filterValue, pageable));
    }

    @PatchMapping("/{prescriptionId}/cancel")
    public ResponseEntity<PrescriptionDTO> cancelPrescription(@PathVariable("prescriptionId") String prescriptionId) {
        return ResponseEntity.ok(prescriptionService.cancelPrescription(prescriptionId));
    }

}
