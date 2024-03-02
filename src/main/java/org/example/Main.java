package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Config config = Config.load();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            OpenAIClient openAIClient = new OpenAIClient(config.getEndpoint(), config.getApiKey(), httpClient, objectMapper);
            Chat chat = new Chat(openAIClient, System.in, System.out, System.err);
            chat.start();
        }

    }
}