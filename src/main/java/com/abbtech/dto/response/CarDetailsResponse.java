package com.abbtech.dto.response;

public record CarDetailsResponse(
        Integer id,
        String engineNumber,
        String registrationCode,
        String color,
        String insuranceNumber,
        String fuelType,
        String engineCapacity
) {
}

