package com.abbtech.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateModelRequest(
        @NotBlank(message = "model id can not be blank")
        @NotNull(message = "name can not be null")
        String name,
        String category,
        Integer yearFrom,
        Integer yearTo) {
}

