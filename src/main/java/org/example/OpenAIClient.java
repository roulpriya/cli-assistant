package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAIClient {

    private final String endpoint;
    private final String apikey;

    private final HttpClient client;

    private final ObjectMapper objectMapper;


    public OpenAIClient(String endpoint, String apikey, HttpClient client, ObjectMapper objectMapper) {
        this.endpoint = endpoint;
        this.apikey = apikey;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public ChatCompletionResponse chatCompletion(ChatCompletionRequest chatCompletionRequest) throws IOException, URISyntaxException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .uri(new URI(endpoint + "/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apikey)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(chatCompletionRequest)))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() != 200) {
            throw new IllegalStateException("Unexpected status code %d".formatted(response.statusCode()));
        }

        return objectMapper.readValue(response.body(), ChatCompletionResponse.class);
    }

}