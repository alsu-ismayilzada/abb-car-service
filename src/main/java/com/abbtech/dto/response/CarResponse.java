package com.abbtech.dto.response;

import com.abbtech.annotations.LogIgnore;
import lombok.Builder;

import java.util.List;

@Builder
public record CarResponse (
        Integer id,
        String vin,
        String registrationNumber,
        @LogIgnore
        Integer mileageKm,
        @LogIgnore
        Integer productionYear,
        Integer modelId,
        @LogIgnore
        CarDetailsResponse carDetails,
        @LogIgnore
        List<FeatureResponse> features
) {
}
