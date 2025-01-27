package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.dto.responses.PatientDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Endpoint to create a new Patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody @Valid PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    @GetMapping
    public ResponseEntity<Page<PatientDTO>> getPatients(
            @RequestParam(required = false, defaultValue = "none") String filterName,
            @RequestParam(required = false, defaultValue = "") String filterValue,
            Pageable pageable
    ) {
        return ResponseEntity.ok(patientService.getPatients(filterName, filterValue, pageable));
    }

    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody @Valid PatientDTO patientDTO) {
        PatientDTO patient = patientService.updatePatient(patientDTO);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
