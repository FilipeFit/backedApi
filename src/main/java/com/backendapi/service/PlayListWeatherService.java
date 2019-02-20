package com.backendapi.service;

import com.backendapi.exception.InvalidInputSearchWeather;
import com.backendapi.exception.WeatherServiceException;
import com.backendapi.model.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayListWeatherService {

  private final WeatherService weatherService;
  private SpotifyService spotifyService;

  @Autowired
  public PlayListWeatherService(final WeatherService weatherService, final SpotifyService spotifyService) {
    this.weatherService = weatherService;
    this.spotifyService = spotifyService;
  }

  public String definePlaylist(Optional<String> cityName, Optional<Integer> latitude,
      Optional<Integer> longitude) {

    WeatherEntity weatherEntity = getWeather(cityName, latitude, longitude);
    return definePlaylistGivenTemperature(weatherEntity.getTemperature());
  }

  private WeatherEntity getWeather(Optional<String> cityName, Optional<Integer> latitude,
      Optional<Integer> longitude) {

    if (cityName.isPresent()) {
      try{
        return weatherService.getWeatherGivenCityName(cityName.get());
      }catch(Exception ex){
        throw new WeatherServiceException(ex.getMessage());
      }
    }
    if (!cityName.isPresent() && latitude.isPresent() && longitude.isPresent()) {
      try{
        return weatherService.getWeatherGivenLatAndLong(latitude.get(), longitude.get());
      }catch(Exception ex){
        throw new WeatherServiceException(ex.getMessage());
      }
    } else {
      throw new InvalidInputSearchWeather();
    }
  }

  private String definePlaylistGivenTemperature(double temperature) {

    if (temperature > 30) {
      return "Party playlist " + spotifyService.getPlayListTracks("Party");
    } else if (temperature <= 30 && temperature >= 15) {
      return "Pop Playlist " + spotifyService.getPlayListTracks("Pop");
    } else if (temperature <= 14 && temperature >= 10) {
      return "Rock playlist " + spotifyService.getPlayListTracks("Rock");
    } else {
      return "Classic playlist " + spotifyService.getPlayListTracks("Classic");
    }
  }
}
