package org.beatrice.dgtuProject.dto;

public class ErrorResponse extends ApiResponse {
    private final String error;

    public ErrorResponse(String error, String message) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
