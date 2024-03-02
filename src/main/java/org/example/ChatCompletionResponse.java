package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ChatCompletionResponse(String id,
                                     String object,
                                     @JsonFormat(pattern = "yyyy-MM-dd HH:mm") Instant created,
                                     String model,
                                     List<Choice> choices,
                                     Usage usage) {
}
