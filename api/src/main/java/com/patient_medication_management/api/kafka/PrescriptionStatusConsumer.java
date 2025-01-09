package com.patient_medication_management.api.kafka;

import com.patient_medication_management.api.prescription.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionStatusConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PrescriptionStatusConsumer.class);
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionStatusConsumer(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @KafkaListener(topics = "prescription_status_events", groupId = "hospital_group")
    public void listen(PrescriptionStatusEvent event) {
        logger.info("Received PrescriptionStatusEvent: [Prescription ID: '{}', New Status: '{}']",
                event.getPrescriptionId(), event.getStatus());
        try {
            prescriptionService.updatePrescriptionStatus(event);
            logger.info("Successfully updated Prescription ID '{}' to Status '{}'",
                    event.getPrescriptionId(), event.getStatus());
        } catch (Exception e) {
            logger.error("Error processing PrescriptionStatusEvent for Prescription ID '{}': {}",
                    event.getPrescriptionId(), e.getMessage(), e);
        }
    }
}
