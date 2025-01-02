package com.patient_medication_management.api.patient;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.prescription.Prescription;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "patient_id", unique = true, nullable = false, length = 8)
    private String id; // Unique 8-character ID

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private String dob;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientGender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
