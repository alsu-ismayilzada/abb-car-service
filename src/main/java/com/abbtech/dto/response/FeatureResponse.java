package com.abbtech.dto.response;

import lombok.Builder;

@Builder
public record FeatureResponse(
        Integer id,
        Integer featureId,
        String name,
        String description,
        String category
) {
}
