package com.patient_medication_management.api.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionEventPublisher.class);
    private static final String TOPIC = "new_prescription_events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishNewPrescription(NewPrescriptionEvent event) {
        logger.info("Attempting to publish NEW_PRESCRIPTION event to topic '{}'. Event details: [Prescription ID: '{}', Patient ID: '{}']",
                TOPIC, event.getPrescriptionId(), event.getPatientId());

        try {
            kafkaTemplate.send(TOPIC, "NEW_PRESCRIPTION", event);
            logger.info("NEW_PRESCRIPTION event for Prescription ID '{}' published successfully to topic '{}'",
                    event.getPrescriptionId(), TOPIC);
        } catch (Exception e) {
            logger.error("Failed to publish NEW_PRESCRIPTION event for Prescription ID '{}' to topic '{}'. Error: {}",
                    event.getPrescriptionId(), TOPIC, e.getMessage(), e);
        }

    }

    public void publishCancelPrescription(CancelPrescriptionEvent event) {
        logger.info("Attempting to publish CANCEL_PRESCRIPTION event to topic '{}'. Event details: [Prescription ID: '{}']",
                TOPIC, event.getPrescriptionId());

        try {
            kafkaTemplate.send(TOPIC, "CANCEL_PRESCRIPTION", event);
            logger.info("CANCEL_PRESCRIPTION event for Prescription ID '{}' published successfully to topic '{}'",
                    event.getPrescriptionId(), TOPIC);
        } catch (Exception e) {
            logger.error("Failed to publish CANCEL_PRESCRIPTION event for Prescription ID '{}' to topic '{}'. Error: {}",
                    event.getPrescriptionId(), TOPIC, e.getMessage(), e);
        }
    }
}
