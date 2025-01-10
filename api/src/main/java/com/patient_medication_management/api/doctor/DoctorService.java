package com.patient_medication_management.api.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.patient_medication_management.api.dto.responses.DoctorDTO;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorDTO getOrCreateDoctorUser(OAuth2User user) {
        String oktaId = user.getAttribute("sub");
        Doctor doctor = doctorRepository.findByOktaId(oktaId).orElseGet(() -> {
            Doctor newDoctor = new Doctor();
            newDoctor.setOktaId(oktaId);
            newDoctor.setFirstName(user.getAttribute("given_name"));
            newDoctor.setLastName(user.getAttribute("family_name"));
            newDoctor.setEmail(user.getAttribute("email"));
            newDoctor.setPhone(user.getAttribute("phone_number"));
            return doctorRepository.save(newDoctor);
        });
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setOktaId(doctor.getOktaId());
        doctorDTO.setFirstName(doctor.getFirstName());
        doctorDTO.setLastName(doctor.getLastName());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setPhone(doctor.getPhone());
        return doctorDTO;
    }

}
