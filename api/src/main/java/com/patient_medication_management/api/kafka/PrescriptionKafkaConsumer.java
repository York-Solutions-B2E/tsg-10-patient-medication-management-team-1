package com.patient_medication_management.api.kafka;

import com.patient_medication_management.api.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PrescriptionKafkaConsumer {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionKafkaConsumer(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }



    @KafkaListener(topics = "prescription_status_updates", groupId = "hospital_group")
    public void consumePrescriptionStatusUpdates(PrescriptionStatusEvent event) {
        prescriptionService.updatePrescriptionStatus(event);
    }
}