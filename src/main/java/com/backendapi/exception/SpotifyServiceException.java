package com.backendapi.exception;

public class SpotifyServiceException extends RuntimeException {

    public SpotifyServiceException(String message) {
        super();
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
