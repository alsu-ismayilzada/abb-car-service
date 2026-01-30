package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateFeatureRequest;
import com.abbtech.dto.response.FeatureResponse;
import com.abbtech.model.Feature;
import com.abbtech.repository.FeatureRepository;
import com.abbtech.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    @Override
    public FeatureResponse getFeatureById(Integer id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found with id: " + id));

        return buildFeatureResponse(feature);
    }

    @Override
    public List<FeatureResponse> getAllFeatures() {
        return featureRepository.findAll()
                .stream()
                .map(this::buildFeatureResponse)
                .toList();
    }

    @Override
    public List<Feature> findAllById(List<Integer> ids) {
        return featureRepository.findAllById(ids);
    }

    @Override
    public void saveFeature(CreateFeatureRequest request) {

        Feature feature = buildFeature(request);
        featureRepository.save(feature);
    }

    @Override
    public void deleteFeatureById(Integer id) {
        if (!featureRepository.existsById(id)) {
            throw new RuntimeException("Feature not found with id: " + id);
        }
        featureRepository.deleteById(id);
    }

    private Feature buildFeature(CreateFeatureRequest request){
        return Feature.builder()
                .featureId(request.featureId())
                .name(request.name())
                .description(request.description())
                .category(request.category())
                .build();
    }

    private FeatureResponse buildFeatureResponse(Feature feature) {
        return FeatureResponse.builder()
                .id(feature.getId())
                .featureId(feature.getFeatureId())
                .name(feature.getName())
                .description(feature.getDescription())
                .category(feature.getCategory())
                .build();
    }
}
