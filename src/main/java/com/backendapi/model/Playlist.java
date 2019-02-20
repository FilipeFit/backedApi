package com.backendapi.model;

import java.util.Objects;

public class Playlist {

  private String message;
  private String trackName;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTrackName() {
    return trackName;
  }

  public void setTrackName(String trackName) {
    this.trackName = trackName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Playlist playlist = (Playlist) o;
    return message.equals(playlist.message) &&
        trackName.equals(playlist.trackName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, trackName);
  }
}
