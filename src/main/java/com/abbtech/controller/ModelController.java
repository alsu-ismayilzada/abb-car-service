package com.abbtech.controller;

import com.abbtech.dto.request.CreateModelRequest;
import com.abbtech.dto.request.UpdateModelRequest;
import com.abbtech.dto.response.ModelResponse;
import com.abbtech.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping("/{id}")
    public ResponseEntity<ModelResponse> getModelById(@PathVariable Integer id) {
        return ResponseEntity.ok(modelService.getModelById(id));
    }

    @GetMapping
    public ResponseEntity<List<ModelResponse>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }

    @PostMapping("/brand/{brandId}")
    public ResponseEntity<Void> saveModel(
            @PathVariable Integer brandId,
            @RequestBody @Valid CreateModelRequest request) {

        modelService.saveModel(brandId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateModel(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateModelRequest request) {

        modelService.updateModel(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelById(@PathVariable Integer id) {
        modelService.deleteModelById(id);
        return ResponseEntity.noContent().build();
    }
}
