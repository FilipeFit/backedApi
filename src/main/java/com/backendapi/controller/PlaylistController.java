package com.backendapi.controller;

import com.backendapi.service.PlayListWeatherService;
import com.backendapi.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

  private final PlayListWeatherService playListWeatherService;
  private final SpotifyService spotifyService;

  @Autowired
  public PlaylistController(PlayListWeatherService playListWeatherService, SpotifyService spotifyService) {
    this.playListWeatherService = playListWeatherService;
    this.spotifyService = spotifyService;
  }

  @GetMapping
  public ResponseEntity<String> returnPlaylistGivenPosition(
      @RequestParam("cityName") Optional<String> cityName,
      @RequestParam("lat") Optional<Integer> latitude,
      @RequestParam("lon") Optional<Integer> longitude) {

    String playlist = playListWeatherService.definePlaylist(cityName, latitude, longitude);

    return ResponseEntity.status(HttpStatus.OK).body(playlist);
  }
}
