package com.abbtech.dto.response;

import com.abbtech.model.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record CarResponse (
        Integer id,
        String vin,
        String registrationNumber,
        Integer mileageKm,
        Integer productionYear,
        Integer modelId,
        CarDetailsResponse carDetails,
        List<FeatureResponse> features
) {
}
