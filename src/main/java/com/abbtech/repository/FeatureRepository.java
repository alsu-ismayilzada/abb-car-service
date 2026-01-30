package com.abbtech.repository;

import com.abbtech.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    Optional<Feature> findByFeatureId(Integer featureId);

    boolean existsByFeatureId(Integer featureId);
}

