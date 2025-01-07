package com.patient_medication_management.api.exception;

public class InvalidPrescriptionStatusException extends RuntimeException {
    public InvalidPrescriptionStatusException(String message) {
        super(message);
    }

    public InvalidPrescriptionStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
