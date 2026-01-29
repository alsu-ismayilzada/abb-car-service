package com.abbtech.dto.request;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCarRequest (
        String vin,
        @NotNull(message = "registration number can not be null")
        String registrationNumber,
        Integer mileageKm,
        @NotNull(message = "production year can not be null")
        Integer productionYear,
        @NotNull(message = "model id can not be null")
        Integer modelId,
        @Valid
        CreateCarDetailsRequest carDetails,
        List<Integer> featureIds
) {
}
