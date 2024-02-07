package org.example;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatCompletionResponse(String id, String object, LocalDateTime created, String model, Choice choice, Usage usage) {
}
