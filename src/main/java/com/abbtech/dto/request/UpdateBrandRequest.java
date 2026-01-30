package com.abbtech.dto.request;

import com.abbtech.dto.ModelDto;
import com.abbtech.validation.BrandGroupA;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateBrandRequest(
        @NotBlank(message = "name can not be empty or null", groups = BrandGroupA.class)
        String name,
        String country,
        @Positive(message = "founded year must be positive")
        @Min(1900)
        @Max(2100)
        Integer foundedYear,
        @Size(min = 1, message = "at least one model is required,max=2", max = 2)
        @Valid List<ModelDto> models
) {
}
