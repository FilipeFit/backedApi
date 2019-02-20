package com.backendapi.service;

import com.backendapi.model.WeatherEntity;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
public class WeatherServiceTest {

  private static final String CITY_URL = "http://api.openweathermap.org/data/2.5/";
  private static final String LOCATION_URL = "https://samples.openweathermap.org/data/2.5/";

  @Autowired
  private WeatherService weatherService;

  @Autowired
  private MockRestServiceServer server;


  @Test
  public void getWeatherGivenCityTest() {
    this.server.expect(
        requestTo(CITY_URL + "weather?q=Campinas&units=metric&APPID=a0f000ef2e1d5ee2626c9b61db96ba9e"))
        .andRespond(withSuccess(
            new ClassPathResource("weather-campinas.json", getClass()),
            MediaType.APPLICATION_JSON));
    WeatherEntity weatherEntity = this.weatherService.getWeatherGivenCityName("Campinas");
    assertThat(weatherEntity.getTemperature()).isEqualTo(286.72, Offset.offset(0.1));
    assertThat(weatherEntity.getWeatherId()).isEqualTo(800);
    assertThat(weatherEntity.getWeatherIcon()).isEqualTo("01d");
    this.server.verify();
  }

  @Test
  public void getWeatherGivenLocation() {
    this.server.expect(
        requestTo(LOCATION_URL + "weather?lat=35&lon=139&units=metric&APPID=a0f000ef2e1d5ee2626c9b61db96ba9e"))
        .andRespond(withSuccess(
            new ClassPathResource("weather-shuzenji.json", getClass()),
            MediaType.APPLICATION_JSON));
    WeatherEntity weatherEntity = this.weatherService.getWeatherGivenLatAndLong(35, 139);
    assertThat(weatherEntity.getTemperature()).isEqualTo(289.5, Offset.offset(0.1));
    assertThat(weatherEntity.getWeatherId()).isEqualTo(804);
    assertThat(weatherEntity.getWeatherIcon()).isEqualTo("04n");
    this.server.verify();
  }


}
