package com.abbtech.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateFeatureRequest(
        Integer featureId,
        @NotNull(message = "name can not be null")
        String name,
        String description,
        @NotNull(message = "category can not be null")
        String category
) {
}
