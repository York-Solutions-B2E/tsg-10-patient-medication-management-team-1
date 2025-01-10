package com.patient_medication_management.api.kafka;

import com.patient_medication_management.api.enums.PrescriptionStatus;
import com.patient_medication_management.api.prescription.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PrescriptionStatusConsumerTest {

    @Mock
    private PrescriptionService prescriptionService;

    @InjectMocks
    private PrescriptionStatusConsumer prescriptionStatusConsumer;

    public PrescriptionStatusConsumerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListen() {
        // Arrange
        PrescriptionStatusEvent event = new PrescriptionStatusEvent("12345", PrescriptionStatus.READY_FOR_PICKUP);

        // Act
        prescriptionStatusConsumer.listen(event);

        // Assert

        verify(prescriptionService, times(1)).updatePrescriptionStatus(event);
    }
}
