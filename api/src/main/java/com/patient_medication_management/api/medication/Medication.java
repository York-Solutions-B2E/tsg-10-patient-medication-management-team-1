package com.patient_medication_management.api.medication;

import com.patient_medication_management.api.prescription.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medicationName;

    @Column(nullable = false, unique = true)
    private String medicationCode;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Medication(String medicationName, String medicationCode) {
        this.medicationName = medicationName;
        this.medicationCode = medicationCode;
    }
}
