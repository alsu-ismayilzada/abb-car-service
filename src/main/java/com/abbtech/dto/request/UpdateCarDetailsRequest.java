package com.abbtech.dto.request;

public record UpdateCarDetailsRequest(
        String engineNumber,
        String registrationCode,
        String color,
        String insuranceNumber,
        String fuelType,
        String engineCapacity
) {
}
