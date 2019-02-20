package com.backendapi.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backendapi.service.PlayListWeatherService;
import com.backendapi.service.SpotifyService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PlaylistControllerTest {

  private MockMvc mockMvc;

  @BeforeClass
  public void setUp() {
    PlaylistController controller = new PlaylistController(mock(PlayListWeatherService.class), mock(SpotifyService.class));
    this.mockMvc =
        MockMvcBuilders
            .standaloneSetup(controller)
            .build();
  }

  @Test
  public void playlistGivenCompleteURL() throws Exception {
    String url = String.format("/playlist?cityName=%s&lat=%s&lon=%s", "Campinas", 10, 10);
    mockMvc.perform(get(url)).andExpect(status().isOk());
  }

  @Test
  public void playlistGivenCity() throws Exception {
    String url = String.format("/playlist?cityName=%s", "Campinas");
    mockMvc.perform(get(url)).andExpect(status().isOk());
  }

  @Test
  public void playlistGivenCoordenates() throws Exception {
    String url = String.format("/playlist?lat=%s&lon=%s", 10, 10);
    mockMvc.perform(get(url)).andExpect(status().isOk());
  }

  @Test
  public void invalidLatAndLonTest() throws Exception {
    mockMvc.perform(get("/playlist?lat=10.0&lon=10.0")).andExpect(status().isBadRequest());
  }

  @Test
  public void invalidparametersApiCall() throws Exception {
    mockMvc.perform(get("/playlist?test=10.0&lon=10.0")).andExpect(status().isBadRequest());
  }
}
