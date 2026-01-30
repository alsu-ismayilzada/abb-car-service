package com.abbtech.controller;

import com.abbtech.dto.request.CreateFeatureRequest;
import com.abbtech.dto.response.FeatureResponse;
import com.abbtech.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> getFeatureById(@PathVariable Integer id) {
        return ResponseEntity.ok(featureService.getFeatureById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeatureResponse>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @PostMapping
    public ResponseEntity<Void> saveFeature(@RequestBody @Valid CreateFeatureRequest request) {
        featureService.saveFeature(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeatureById(@PathVariable Integer id) {
        featureService.deleteFeatureById(id);
        return ResponseEntity.noContent().build();
    }
}
