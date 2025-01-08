package com.patient_medication_management.api.kafka;

import com.patient_medication_management.api.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionStatusConsumer {
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionStatusConsumer(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @KafkaListener(topics = "prescription_status_events", groupId = "hospital_group")
    public void consumePrescriptionStatusEvent(PrescriptionStatusEvent event) {
        prescriptionService.updatePrescriptionStatus(event);
    }


}
