package com.backendapi.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotifyCredentialsService {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("55e7537f3cb749aa9100f9359676dbfd")
            .setClientSecret("afecf7d82b3c4cdd9284e3092d6e326e")
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public final String clientCredentials() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return spotifyApi.getAccessToken();
    }
}
