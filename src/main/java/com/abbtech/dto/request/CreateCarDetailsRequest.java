package com.abbtech.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateCarDetailsRequest(
        @NotNull(message = "engine number can not be null")
        String engineNumber,
        @NotNull(message = "registration code can not be null")
        String registrationCode,
        String color,
        @NotNull(message = "insurance number can not be null")
        String insuranceNumber,
        String fuelType,
        @NotNull(message = "engine capacity can not be null")
        String engineCapacity
) {
}
