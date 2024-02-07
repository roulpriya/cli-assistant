package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.out.println("Hello world!");

        ObjectMapper objectMapper = new ObjectMapper();

        Config config = Config.load();
        try (HttpClient httpClient = HttpClient.newHttpClient()) {

            OpenAIClient openAIClient = new OpenAIClient(config.getEndpoint(), config.getApiKey(), httpClient, objectMapper);

            System.out.println(config.getEndpoint());
            System.out.println(openAIClient.chatCompletion());
        }

    }
    }