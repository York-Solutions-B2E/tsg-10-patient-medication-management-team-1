package com.patient_medication_management.api.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionKafkaProducer {

    private static final String TOPIC = "patient_prescription_events";

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionKafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public PrescriptionKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNewPrescriptionEvent(NewPrescriptionEvent event) {
        try {
            kafkaTemplate.send(TOPIC, "NEW_PRESCRIPTION", event);
            logger.info("New prescription event sent to topic: " + TOPIC);
        } catch (Exception e) {
            logger.error("Unexpected error while publishing NEW_PRESCRIPTION event", e);
        }
    }

    public void sendCancelEvent(CancelPrescriptionEvent event) {
        try {
            kafkaTemplate.send(TOPIC, "CANCEL_PRESCRIPTION", event);
            logger.info("Cancel prescription event sent to topic: " + TOPIC);
        } catch (Exception e) {
            logger.error("Unexpected error while publishing CANCEL event", e);
        }
    }
}