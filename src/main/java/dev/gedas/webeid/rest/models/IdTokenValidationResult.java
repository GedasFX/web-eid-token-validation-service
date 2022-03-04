package dev.gedas.webeid.rest.models;

public class IdTokenValidationResult {
    private final String status;
    private final String message;

    public IdTokenValidationResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
