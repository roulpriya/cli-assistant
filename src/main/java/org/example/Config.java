package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Properties;

@Getter
@Setter
public class Config {
    private final String endpoint;

    private final String apiKey;

    public Config(String endpoint, String apiKey) {
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }


    public static Config load() {

        try (var stream = Config.class.getResourceAsStream("/application.properties")) {
            Properties properties = new Properties();
            properties.load(stream);

            return new Config(properties.getProperty("config.endpoint"), properties.getProperty("config.api.key"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
