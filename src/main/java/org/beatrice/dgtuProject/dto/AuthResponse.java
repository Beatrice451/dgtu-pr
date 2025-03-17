package org.beatrice.dgtuProject.dto;

public class AuthResponse extends ApiResponse {
    private final String token;

    public AuthResponse(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
