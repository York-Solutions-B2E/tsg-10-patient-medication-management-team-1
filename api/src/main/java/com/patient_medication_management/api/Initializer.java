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

import java.time.LocalDateTime;
import java.util.List;
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
        Prescription prescription1 = new Prescription(
                "PRK8A",
                "Take 2 tablets daily after meals",
                PrescriptionStatus.SENT,
                "500mg",
                30,
                medication1,
                doctor1,
                pharmacy1,
                patient1
        );
        List<Prescription> prescriptions = List.of(
                new Prescription(
                        "PRK8A",
                        "Take 2 tablets daily after meals",
                        PrescriptionStatus.SENT,
                        "500mg",
                        30,
                        medication1,
                        doctor1,
                        pharmacy1,
                        patient1
                ),
                new Prescription("PRK9D", "Take 2 tablets daily with food", PrescriptionStatus.PICKED_UP, "50mg", 30, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK10E", "Apply a thin layer to affected area twice a day", PrescriptionStatus.SENT, "10mg/g", 40, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK11F", "Take 1 tablet every 6 hours as needed for fever", PrescriptionStatus.RECEIVED, "100mg", 24, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK12G", "Take 1 tablet every 12 hours for 7 days", PrescriptionStatus.BACK_ORDERED, "250mg", 14, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK13H", "Take 2 tablets before bed", PrescriptionStatus.READY_FOR_PICKUP, "5mg", 15, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK14I", "Take 1 tablet every 8 hours for 10 days", PrescriptionStatus.PICKED_UP, "300mg", 30, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK15J", "Take 1 tablet every 4 hours for 5 days", PrescriptionStatus.RECEIVED, "200mg", 20, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK16K", "Take 1 tablet every 6 hours for 7 days", PrescriptionStatus.BACK_ORDERED, "150mg", 25, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK17L", "Take 2 tablets in the morning and 1 tablet at night", PrescriptionStatus.READY_FOR_PICKUP, "50mg", 40, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK18M", "Take 1 tablet daily", PrescriptionStatus.SENT, "100mg", 30, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK19N", "Take 1 tablet every 3 hours as needed for pain", PrescriptionStatus.PICKED_UP, "200mg", 15, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK20O", "Take 1 tablet daily in the morning", PrescriptionStatus.RECEIVED, "150mg", 28, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK21P", "Apply a patch once a week", PrescriptionStatus.READY_FOR_PICKUP, "5mg/day", 1, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK22Q", "Take 2 tablets every 12 hours", PrescriptionStatus.SENT, "75mg", 30, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK23R", "Take 1 tablet every 8 hours for 7 days", PrescriptionStatus.BACK_ORDERED, "250mg", 21, medication2, doctor2, pharmacy2, patient2),
                new Prescription("PRK24S", "Take 1 tablet twice a day with food", PrescriptionStatus.RECEIVED, "100mg", 60, medication2, doctor2, pharmacy2, patient2)
        );



        // Save prescriptions
        prescriptionRepository.saveAll(prescriptions);

    }

    private String generateUniquePatientId() {
        String patientId;
        do {
            patientId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        } while (patientRepository.existsByPatientId(patientId)); // Check if it already exists
        return patientId;
    }

}
