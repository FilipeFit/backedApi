package com.backendapi.service;

import com.backendapi.exception.SpotifyServiceException;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    private final SpotifyCredentialsService spotifyCredentialsService;

    @Autowired
    public SpotifyService(final SpotifyCredentialsService spotifyCredentialsService) {
        this.spotifyCredentialsService = spotifyCredentialsService;
    }

    public String getPlayListTracks(String playlist) {

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyCredentialsService.clientCredentials())
                .build();

        final SearchPlaylistsRequest searchPlaylistsRequest = spotifyApi.searchPlaylists(playlist)
                .market(CountryCode.SE)
                .limit(1)
                .offset(0)
                .build();
        List<String> tracks = new ArrayList<>();
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = searchPlaylistsRequest.execute();
            PlaylistSimplified[] playlistSimplifieds = playlistSimplifiedPaging.getItems();
            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistTracks(spotifyApi, playlistSimplifieds[0]);
            PlaylistTrack[] playlistTracks = playlistTrackPaging.getItems();
            for (PlaylistTrack track: playlistTracks) {
                tracks.add(track.getTrack().getName());
            }

        } catch (Exception ex) {
            throw new SpotifyServiceException(ex.getMessage());
        }
        return tracks.toString();
    }

    private Paging<PlaylistTrack> getPlaylistTracks(SpotifyApi spotifyApi, PlaylistSimplified playlistSimplified)
            throws IOException, SpotifyWebApiException {
        final GetPlaylistsTracksRequest getPlaylistsTracksRequest = spotifyApi
                .getPlaylistsTracks(playlistSimplified.getId())
                .limit(10)
                .offset(0)
                .build();
        return getPlaylistsTracksRequest.execute();
    }
}
