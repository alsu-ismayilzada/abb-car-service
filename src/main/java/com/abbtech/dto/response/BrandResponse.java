package com.abbtech.dto.response;

import com.abbtech.dto.ModelDto;

import java.util.List;

public record BrandResponse(
        Integer id,
        String name,
        String country,
        List<ModelDto> models
) {
}

