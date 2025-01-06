package com.patient_medication_management.api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerUnitTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Act
        ResponseEntity<ApiError> response = globalExceptionHandler.handleResourceNotFoundException(exception);
        ApiError error = response.getBody();

        // Assert
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, error.getStatus());
        assertEquals(errorMessage, error.getMessage());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void handleIllegalStateException_ShouldReturnBadRequestStatus() {
        // Arrange
        String errorMessage = "Cannot cancel prescription";
        IllegalStateException exception = new IllegalStateException(errorMessage);

        // Act
        ResponseEntity<ApiError> response = globalExceptionHandler.handleIllegalStateException(exception);
        ApiError error = response.getBody();

        // Assert
        assertNotNull(error);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, error.getStatus());
        assertEquals(errorMessage, error.getMessage());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void handleInvalidStatusException_ShouldReturnBadRequestStatus() {
        // Arrange
        String errorMessage = "Invalid status: UNKNOWN";
        InvalidPrescriptionStatusException exception = new InvalidPrescriptionStatusException(errorMessage);

        // Act
        ResponseEntity<ApiError> response = globalExceptionHandler.handleInvalidStatusException(exception);
        ApiError error = response.getBody();

        // Assert
        assertNotNull(error);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, error.getStatus());
        assertEquals(errorMessage, error.getMessage());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        // Arrange
        String errorMessage = "Unexpected error";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<ApiError> response = globalExceptionHandler.handleGenericException(exception);
        ApiError error = response.getBody();

        // Assert
        assertNotNull(error);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, error.getStatus());
        assertTrue(error.getMessage().contains(errorMessage));
        assertNotNull(error.getTimestamp());
    }
}
