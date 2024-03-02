package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Chat {
    private final OpenAIClient openAIClient;
    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final PrintStream errorStream;

    public Chat(OpenAIClient openAIClient, InputStream inputStream, PrintStream outputStream, PrintStream errorStream) {

        this.openAIClient = openAIClient;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    public void start() throws InterruptedException, IOException, URISyntaxException {
        outputStream.println("Welcome to Your CLI Assistant!");

        try (var stream = getClass().getResourceAsStream("/prompt.txt"); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            if (stream == null) {
                throw new IllegalStateException("Prompt not found");
            }
            String prompt = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            String userInput;
            while (true) {
                outputStream.println("Enter your text (or 'quit' to exit): ");
                userInput = reader.readLine();
                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                        .model("codellama/CodeLlama-34b-Instruct-hf")
                        .messages(List.of(
                                new Message("system", prompt),
                                new Message("user", userInput)
                        ))
                        .temperature(0.7)
                        .build();

                try {
                    ChatCompletionResponse response = openAIClient.chatCompletion(chatCompletionRequest);
                    outputStream.println("🤖 Assistant:\n" + response.choices().getFirst().message().content());
                } catch (IOException e) {
                    errorStream.println("Error in fetching response from server: " + e.getMessage());
                }
            }
        }
    }

}
