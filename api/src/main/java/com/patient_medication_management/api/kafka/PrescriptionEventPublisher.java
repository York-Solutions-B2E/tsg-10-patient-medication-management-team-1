package com.patient_medication_management.api.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionEventPublisher {
    private static final String TOPIC = "new_prescription_events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishNewPrescription(NewPrescriptionEvent event) {
        kafkaTemplate.send(TOPIC, "NEW_PRESCRIPTION", event);
    }

    public void publishCancelPrescription(CancelPrescriptionEvent event) {
        kafkaTemplate.send(TOPIC, "CANCEL_PRESCRIPTION", event);
    }
}
