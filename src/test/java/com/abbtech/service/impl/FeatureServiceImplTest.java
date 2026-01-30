package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateFeatureRequest;
import com.abbtech.dto.response.FeatureResponse;
import com.abbtech.exception.FeatureException;
import com.abbtech.model.Feature;
import com.abbtech.repository.FeatureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FeatureServiceImplTest {

    @Mock
    private FeatureRepository featureRepository;

    @InjectMocks
    private FeatureServiceImpl featureService;

    @Test
    void getFeatureById_success() {
        Feature feature = Feature.builder().id(1).featureId(100).name("ABS").description("Anti-lock Brakes").category("Safety").build();
        when(featureRepository.findById(1)).thenReturn(Optional.of(feature));

        FeatureResponse response = featureService.getFeatureById(1);

        Assertions.assertEquals(1, response.id());
        Assertions.assertEquals(100, response.featureId());
        Assertions.assertEquals("ABS", response.name());
        Assertions.assertEquals("Anti-lock Brakes", response.description());
        Assertions.assertEquals("Safety", response.category());
    }

    @Test
    void getFeatureById_notFound() {
        when(featureRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(FeatureException.class,
                () -> featureService.getFeatureById(1));
    }

    @Test
    void getAllFeatures_success() {
        Feature feature1 = Feature.builder().id(1).featureId(100).name("ABS").description("Anti-lock Brakes").category("Safety").build();
        Feature feature2 = Feature.builder().id(2).featureId(101).name("Airbag").description("Front Airbags").category("Safety").build();
        when(featureRepository.findAll()).thenReturn(List.of(feature1, feature2));

        List<FeatureResponse> responseList = featureService.getAllFeatures();

        Assertions.assertEquals(2, responseList.size());
        Assertions.assertEquals("ABS", responseList.get(0).name());
        Assertions.assertEquals("Airbag", responseList.get(1).name());
    }

    @Test
    void findAllById_success() {
        Feature feature1 = Feature.builder().id(1).featureId(100).build();
        Feature feature2 = Feature.builder().id(2).featureId(101).build();
        when(featureRepository.findAllById(List.of(1, 2))).thenReturn(List.of(feature1, feature2));

        List<Feature> features = featureService.findAllById(List.of(1, 2));

        Assertions.assertEquals(2, features.size());
    }

    @Test
    void saveFeature_success() {
        CreateFeatureRequest request = new CreateFeatureRequest(100, "ABS", "Anti-lock Brakes", "Safety");

        Assertions.assertDoesNotThrow(() -> featureService.saveFeature(request));

        verify(featureRepository, times(1)).save(any(Feature.class));
    }

    @Test
    void deleteFeatureById_success() {
        when(featureRepository.existsById(1)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> featureService.deleteFeatureById(1));

        verify(featureRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteFeatureById_notFound() {
        when(featureRepository.existsById(anyInt())).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> featureService.deleteFeatureById(1));

        verify(featureRepository, never()).deleteById(anyInt());
    }
}
