package com.example.DATN.exceptions;

public class ValidationError {
    private String field;
    private String status;
    private String message;

    public ValidationError(String field, String status, String message) {
        this.field = field;
        this.status = status;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


