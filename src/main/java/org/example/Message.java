package org.example;

import lombok.Builder;

@Builder
public record Message(String role, String content) {
}
