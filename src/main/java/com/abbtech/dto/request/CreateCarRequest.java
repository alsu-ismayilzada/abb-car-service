package com.abbtech.dto.request;
import com.abbtech.annotations.LogIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateCarRequest (
        String vin,
        @NotNull(message = "registration number can not be null")
        String registrationNumber,
        @LogIgnore
        Integer mileageKm,
        @NotNull(message = "production year can not be null")
        @LogIgnore
        Integer productionYear,
        @NotNull(message = "model id can not be null")
        Integer modelId,
        @Valid
        CreateCarDetailsRequest carDetails,
        @Size(min = 1, message = "at least one feature is required")
        @LogIgnore
        List<Integer> featureIds
) {
}
