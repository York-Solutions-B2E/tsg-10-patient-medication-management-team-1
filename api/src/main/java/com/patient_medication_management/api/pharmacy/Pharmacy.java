package com.patient_medication_management.api.pharmacy;

import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.prescription.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "fax")
    private String fax;

    // One Pharmacy can have many Patients
    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Patient> patients;

    // One Pharmacy can have many Prescriptions
    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;
}
