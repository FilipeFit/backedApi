package com.backendapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.backendapi.exception.InvalidInputSearchWeather;
import com.backendapi.exception.SpotifyServiceException;
import com.backendapi.exception.WeatherServiceException;
import com.backendapi.model.WeatherEntity;
import org.testng.annotations.Test;

import java.util.Optional;

public class PlayListWeatherServiceTest {

  private final String popMusicPlaylist = "testePop 1, testePop 2";
  private final String rockMusicPlaylist = "testeRock 1, testeRock 2";
  private final String classicMusicPlaylist = "testeClassic 1, testeClassic 2";
  private final String partyMusicPlaylist = "testeParty 1, testeParty 2";

  private WeatherService mockWeatherService() {
    return mock(WeatherService.class);
  }
  private SpotifyService mockSpotifyService(){ return mock(SpotifyService.class);}

  private PlayListWeatherService mockPlayListWeatherService(WeatherService mockedWeatherService, SpotifyService
          mockSpotifyService) {

    WeatherEntity campinasWeatherEntity = new WeatherEntity();
    campinasWeatherEntity.setTemperature(31);
    WeatherEntity santaCatarinaWeatherEntity = new WeatherEntity();
    santaCatarinaWeatherEntity.setTemperature(30);
    WeatherEntity torontoWeatherEntity = new WeatherEntity();
    torontoWeatherEntity.setTemperature(14);
    WeatherEntity victoriaWeatherEntity = new WeatherEntity();
    victoriaWeatherEntity.setTemperature(9);

    when(mockSpotifyService.getPlayListTracks("Pop")).thenReturn(popMusicPlaylist);
    when(mockSpotifyService.getPlayListTracks("Rock")).thenReturn(rockMusicPlaylist);
    when(mockSpotifyService.getPlayListTracks("Party")).thenReturn(partyMusicPlaylist);
    when(mockSpotifyService.getPlayListTracks("Classic")).thenReturn(classicMusicPlaylist);
    when(mockSpotifyService.getPlayListTracks("test")).thenThrow(new SpotifyServiceException("Playlist inválida"));

    when(mockedWeatherService.getWeatherGivenCityName("Campinas")).thenReturn(campinasWeatherEntity);
    when(mockedWeatherService.getWeatherGivenCityName("Santa Catarina")).thenReturn(santaCatarinaWeatherEntity);
    when(mockedWeatherService.getWeatherGivenCityName("Toronto")).thenReturn(torontoWeatherEntity);
    when(mockedWeatherService.getWeatherGivenCityName("Victoria")).thenReturn(victoriaWeatherEntity);
    when(mockedWeatherService.getWeatherGivenCityName("test")).thenThrow(new WeatherServiceException("cidade inválida"));
    when(mockedWeatherService.getWeatherGivenLatAndLong(10, 10)).thenReturn(campinasWeatherEntity);
    when(mockedWeatherService.getWeatherGivenLatAndLong(20, 20)).thenReturn(santaCatarinaWeatherEntity);
    when(mockedWeatherService.getWeatherGivenLatAndLong(30, 30)).thenReturn(torontoWeatherEntity);
    when(mockedWeatherService.getWeatherGivenLatAndLong(40, 40)).thenReturn(victoriaWeatherEntity);
    when(mockedWeatherService.getWeatherGivenLatAndLong(40000, 40000)).thenThrow(new WeatherServiceException("cidade inválida"));

    return new PlayListWeatherService(mockedWeatherService, mockSpotifyService);
  }

  @Test(expectedExceptions = InvalidInputSearchWeather.class)
  public void nullInputTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    playListWeatherService.definePlaylist(Optional.empty(), Optional.empty(),
        Optional.empty());
  }

  @Test(expectedExceptions = WeatherServiceException.class)
  public void invalidCityName() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    playListWeatherService.definePlaylist(Optional.of("test"), Optional.empty(),
            Optional.empty());
  }

  @Test
  public void latLonPartyTimeTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.empty(), Optional.of(10),
            Optional.of(10));
    assertThat(playlist).isEqualTo("Party playlist testeParty 1, testeParty 2");
  }

  @Test
  public void latLonPopMusicTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.empty(), Optional.of(20),
            Optional.of(20));
    assertThat(playlist).isEqualTo("Pop Playlist testePop 1, testePop 2");
  }

  @Test
  public void latLonRockTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.empty(), Optional.of(30),
            Optional.of(30));
    assertThat(playlist).isEqualTo("Rock playlist testeRock 1, testeRock 2");
  }

  @Test
  public void latLonClassicTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.empty(), Optional.of(40),
            Optional.of(40));
    assertThat(playlist).isEqualTo("Classic playlist testeClassic 1, testeClassic 2");
  }


  @Test
  public void cityNamePartyTimeTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.of("Campinas"), Optional.empty(),
            Optional.empty());
    assertThat(playlist).isEqualTo("Party playlist testeParty 1, testeParty 2");
  }

  @Test
  public void cityNamePopMusicTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.of("Santa Catarina"), Optional.empty(),
            Optional.empty());
    assertThat(playlist).isEqualTo("Pop Playlist testePop 1, testePop 2");
  }

  @Test
  public void cityNameRockMusicTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.of("Toronto"), Optional.empty(),
            Optional.empty());
    assertThat(playlist).isEqualTo("Rock playlist testeRock 1, testeRock 2");
  }

  @Test
  public void cityNameClassicMusicTemperatureTest() {
    WeatherService mockWeatherService = mockWeatherService();
    SpotifyService mockSpotifyService = mockSpotifyService();
    PlayListWeatherService playListWeatherService = mockPlayListWeatherService(mockWeatherService, mockSpotifyService);
    String playlist =
        playListWeatherService.definePlaylist(Optional.of("Victoria"), Optional.empty(),
            Optional.empty());
    assertThat(playlist).isEqualTo("Classic playlist testeClassic 1, testeClassic 2");
  }

}
