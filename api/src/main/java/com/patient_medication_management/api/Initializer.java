package com.patient_medication_management.api;

import com.patient_medication_management.api.address.Address;
import com.patient_medication_management.api.address.AddressRepository;
import com.patient_medication_management.api.doctor.Doctor;
import com.patient_medication_management.api.doctor.DoctorRepository;
import com.patient_medication_management.api.enums.PatientGender;
import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.medication.Medication;
import com.patient_medication_management.api.medication.MedicationRepository;
import com.patient_medication_management.api.patient.Patient;
import com.patient_medication_management.api.patient.PatientRepository;
import com.patient_medication_management.api.pharmacy.Pharmacy;
import com.patient_medication_management.api.pharmacy.PharmacyRepository;
import com.patient_medication_management.api.prescription.Prescription;
import com.patient_medication_management.api.prescription.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Initializer implements CommandLineRunner {

    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicationRepository medicationRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public Initializer(
            AddressRepository addressRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            MedicationRepository medicationRepository,
            PharmacyRepository pharmacyRepository,
            PrescriptionRepository prescriptionRepository) {
        this.addressRepository = addressRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicationRepository = medicationRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void run(String... args) {
        // Addresses
        Address address1 = addressRepository.save(new Address("123 Main St", "Apt 4B", "Boston", "MA", "02108"));
        Address address2 = addressRepository.save(new Address("456 Park Ave", "Suite 201", "Cambridge", "MA", "02139"));

        // Doctors
        Doctor doctor1 = doctorRepository.save(new Doctor("Robert", "Wilson", "rwilson@hospital.com", "555-0201"));
        Doctor doctor2 = doctorRepository.save(new Doctor("Sarah", "Brown", "sbrown@hospital.com", "555-0202"));

        // Medications
        Medication medication1 = medicationRepository.save(new Medication("Amoxicillin", "AMX100"));
        Medication medication2 = medicationRepository.save(new Medication("Ibuprofen", "IBU200"));

        // Pharmacies
        Pharmacy pharmacy1 = pharmacyRepository.save(new Pharmacy("CVS", "789 Health St, Boston, MA", "555-030-1000", "1000"));
        Pharmacy pharmacy2 = pharmacyRepository.save(new Pharmacy("Walgreens", "321 Medicine Ave, Cambridge, MA", "555-030-2000", "2000"));

        // Patients
        Patient patient1 = new Patient(generateUniquePatientId(), "John", "Doe", "john@email.com", "555-0101",
                "1980-01-01", PatientGender.MALE, address1);
        Patient patient2 = new Patient(generateUniquePatientId(), "Jane", "Smith", "jane@email.com", "555-0102",
                "1985-02-15", PatientGender.FEMALE, address2);

        patientRepository.save(patient1);
        patientRepository.save(patient2);


        // Prescriptions
        Prescription prescription1 = new Prescription("Take twice daily", "PRF9S",
                PrescriptionStatus.READY_FOR_PICKUP, "2024-01-01", "500mg", 30, medication1, doctor1, pharmacy1, patient1);

        Prescription prescription2 = new Prescription("Take as needed", "PR8KA",
                PrescriptionStatus.SENT, "2024-01-02", "200mg", 60, medication2, doctor2, pharmacy2, patient2);

        prescription1.setPatient(patient1);
        prescription2.setPatient(patient2);

        // Save prescriptions
        prescriptionRepository.save(prescription1);
        prescriptionRepository.save(prescription2);
    }

    private String generateUniquePatientId() {
        String patientId;
        do {
            patientId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (patientRepository.existsById(patientId)); // Check if it already exists
        return patientId;
    }

}
