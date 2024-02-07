package org.example;

import lombok.Builder;

@Builder
public record Usage(int promptTokens, int completionToken, int totalTokens) {
}
