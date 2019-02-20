package com.backendapi.exception;

public class WeatherServiceException extends RuntimeException {

  public WeatherServiceException(String message) {
    super();
    this.message = message;
  }

  private String message;

  @Override
  public String getMessage() {
    return message;
  }
}
