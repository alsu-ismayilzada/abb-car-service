package com.abbtech.dto.response;

import lombok.Builder;

@Builder
public record ModelResponse(
        Integer id,
        String name,
        String category,
        Integer yearFrom,
        Integer yearTo) {
}

