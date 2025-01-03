package com.patient_medication_management.api.seeder;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.prescription.Prescription;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.patient.PatientRepository;
import com.patient_medication_management.api.doctor.DoctorRepository;
import com.patient_medication_management.api.medication.MedicationRepository;
import com.patient_medication_management.api.pharmacy.PharmacyRepository;
import com.patient_medication_management.api.prescription.PrescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.patient_medication_management.api.enums.PrescriptionStatus.SENT;

@Component
public class Seeder implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicationRepository medicationRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AddressRepository addressRepository;

    public Seeder(PatientRepository patientRepository, DoctorRepository doctorRepository,
                  MedicationRepository medicationRepository, PharmacyRepository pharmacyRepository,
                  PrescriptionRepository prescriptionRepository, AddressRepository addressRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicationRepository = medicationRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create Address Data
        Address address1 = new Address(null, "123 Main St", null, "Springfield", "IL", "62701", null);
        Address address2 = new Address(null, "456 Oak Ave", null, "Chicago", "IL", "60601", null);
        Address address3 = new Address(null, "789 Pine Rd", null, "Peoria", "IL", "61602", null);

        // Save Addresses
        List<Address> addresses = addressRepository.saveAll(Arrays.asList(address1, address2, address3));

        // Create Pharmacy Data
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName("Healthy Pharmacy");
        pharmacy.setAddress("456 Pharmacy Rd");
        pharmacy.setPhone("555-1234");
        pharmacy = pharmacyRepository.save(pharmacy);

        // Create Doctor Data
        Doctor doctor = new Doctor(null, "John", "Doe", "doctor123", "doctor@example.com", "555-5678", null);
        doctor = doctorRepository.save(doctor);

        // Create Medication Data
        Medication medication = new Medication();
        medication.setName("Aspirin");
        medication.setCode("MED001");
        medication = medicationRepository.save(medication);

        // Create 10 Patient Data
        for (int i = 1; i <= 10; i++) {
            String email = "patient" + i + "@example.com";

            // Create Patient with proper setter methods
            Patient patient = new Patient();
            patient.setId("P" + String.format("%07d", i));  // Set ID with 7-digit formatting
            patient.setFirstName("FirstName" + i);
            patient.setLastName("LastName" + i);
            patient.setEmail(email);
            patient.setPhone("555-000" + i);
            patient.setDob("1990-01-01");
            patient.setGender(PatientGender.values()[i % 2]);
            patient.setAddress(addresses.get(i % 3));
            patient.setPharmacy(pharmacy);

            // Save Patient
            patient = patientRepository.save(patient);

            // Create Prescription Data for each patient
            Prescription prescription = new Prescription(
                    null,
                    "Take one tablet daily",
                    SENT,
                    "2023-01-01",
                    "500mg",
                    30,
                    medication,
                    doctor,
                    pharmacy,
                    patient,  // Link the prescription to the patient
                    null,
                    null
            );

            // Save Prescription
            prescriptionRepository.save(prescription);
        }

        System.out.println("Database seeded with 10 patients successfully!");
    }
}
