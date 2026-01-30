package com.abbtech.service;

import com.abbtech.dto.request.CreateFeatureRequest;
import com.abbtech.dto.response.FeatureResponse;
import com.abbtech.model.Feature;

import java.util.List;

public interface FeatureService {

    FeatureResponse getFeatureById(Integer id);

    List<FeatureResponse> getAllFeatures();

    List<Feature> findAllById(List<Integer> ids);

    void saveFeature(CreateFeatureRequest request);

    void deleteFeatureById(Integer id);
}

