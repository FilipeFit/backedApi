package com.backendapi.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class BackEndApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  public BackEndApiExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler({InvalidInputSearchWeather.class})
  public ResponseEntity<Object> handleInvalidInputSearchWeather(
      InvalidInputSearchWeather ex, WebRequest request) {

    String userMessage =
        messageSource.getMessage("parametros.invalidos", null, LocaleContextHolder.getLocale());
    String devMessage = ex.toString();
    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({WeatherServiceException.class})
  public ResponseEntity<Object> handleWeatherServiceException(
      WeatherServiceException ex, WebRequest request) {

    String userMessage =
        messageSource.getMessage("servico.previsao.erro", null, LocaleContextHolder.getLocale());
    String devMessage = ex.getMessage();
    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({SpotifyServiceException.class})
  public ResponseEntity<Object> handleSpotifyServiceException(
          SpotifyServiceException ex, WebRequest request) {

    String userMessage =
            messageSource.getMessage("spotify.erro", null, LocaleContextHolder.getLocale());
    String devMessage = ex.getMessage();
    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  public static class Error {

    private String userMessage;
    private String devMessage;

    public Error(String userMessage, String devMessage) {
      this.userMessage = userMessage;
      this.devMessage = devMessage;
    }

    public String getUserMessage() {
      return userMessage;
    }

    public String getDevMessage() {
      return devMessage;
    }
  }

}
