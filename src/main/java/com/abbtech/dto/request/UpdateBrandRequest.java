package com.abbtech.dto.request;

import com.abbtech.dto.ModelDto;

import java.util.List;

public record UpdateBrandRequest  (
        String country,
        Integer foundedYear,
        List<ModelDto> models
) {
}