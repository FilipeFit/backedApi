package com.backendapi.service;

import com.backendapi.model.WeatherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class WeatherService {
  private static final String CITY_WEATHER_URL =
      "http://api.openweathermap.org/data/2.5/weather?q={city}&units=metric&APPID={key}";
  private static final String LAT_LONG_WEATHER_URL =
      "https://samples.openweathermap.org/data/2.5/weather?lat={latitude}&lon={longitude}&units=metric&APPID={key}";

  private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

  private final RestTemplate restTemplate;

  private final String apiKey;

  public WeatherService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
    this.apiKey = "a0f000ef2e1d5ee2626c9b61db96ba9e";
  }

  @Cacheable("cityWeather")
  public WeatherEntity getWeatherGivenCityName(String cityName) {
    logger.info("Requesting current weather for {}", cityName);
    URI url = new UriTemplate(CITY_WEATHER_URL).expand(cityName, this.apiKey);

    return invoke(url, WeatherEntity.class);
  }

  @Cacheable("coordinatesWeather")
  public WeatherEntity getWeatherGivenLatAndLong(Integer latitude, Integer longitude) {
    logger.info("Requesting current weather for coordinates {}, {}", latitude, longitude);
    URI url = new UriTemplate(LAT_LONG_WEATHER_URL).expand(latitude, longitude, this.apiKey);

    return invoke(url, WeatherEntity.class);
  }

  private <T> T invoke(URI url, Class<T> responseType) {
    RequestEntity<?> request = RequestEntity.get(url)
        .accept(MediaType.APPLICATION_JSON).build();
    ResponseEntity<T> exchange = this.restTemplate
        .exchange(request, responseType);
    return exchange.getBody();
  }

}
