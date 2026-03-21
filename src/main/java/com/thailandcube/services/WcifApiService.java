package com.thailandcube.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.thailandcube.models.PublicWcif;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

public class WcifApiService {
    private static final WcifApiService instance = new WcifApiService();

    private final HttpClient httpClient;
    private final Gson gson;

    private WcifApiService() {
        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
            })
        .create();
    }

    public static WcifApiService getInstance() {
        return instance;
    }

    public CompletableFuture<PublicWcif> fetchWcifData(String competitionId) {
        String url = "https://www.worldcubeassociation.org/api/v0/competitions/" + competitionId + "/wcif/public";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
                if (response.statusCode() != 200)
                    throw new RuntimeException("HTTP Status: " + response.statusCode());

                return gson.fromJson(response.body(), PublicWcif.class);
            }
        );
    }
}