package com.patient_medication_management.api.prescription;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(nullable = false, unique = true)
    private String prescriptionId; // Unique ID assigned on submission

    @Column(nullable = false)
    private String instructions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrescriptionStatus status;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Patient patient;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Prescription(String instructions, String prescriptionId, PrescriptionStatus status, String issueDate, String dosage, Integer quantity,
                        Medication medication, Doctor doctor, Pharmacy pharmacy, Patient patient) {
        this.instructions = instructions;
        this.prescriptionId = prescriptionId;
        this.status = status;
        this.issueDate = issueDate;
        this.dosage = dosage;
        this.quantity = quantity;
        this.medication = medication;
        this.doctor = doctor;
        this.pharmacy = pharmacy;
        this.patient = patient;
    }
}
