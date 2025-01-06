package com.patient_medication_management.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

// This is the Error Response Model
@Data
@AllArgsConstructor
public class ApiError {
    private int status; // HTTP status code
    private String message;  // Error message
    private LocalDateTime timestamp; // When the error occurred
}
