package com.abbtech.dto.request;

import java.util.List;

public record UpdateCarRequest(
        String vin,
        String registrationNumber,
        Integer mileageKm,
        Integer productionYear,
        Integer modelId,
        UpdateCarDetailsRequest carDetails,
        List<Integer> featureIds
) {
}
