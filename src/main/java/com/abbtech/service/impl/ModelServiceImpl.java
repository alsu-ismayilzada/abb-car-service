package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateModelRequest;
import com.abbtech.dto.request.UpdateModelRequest;
import com.abbtech.dto.response.ModelResponse;
import com.abbtech.model.Model;
import com.abbtech.repository.ModelRepository;
import com.abbtech.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    @Override
    public Model findById(Integer id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found with id: " + id));
    }

    @Override
    public ModelResponse getModelById(Integer id) {
        var model = findById(id);
        return buildModelResponse(model);
    }

    @Override
    public List<ModelResponse> getAllModels() {
        return modelRepository.findAll().stream()
                .map(this::buildModelResponse)
                .toList();
    }

    @Override
    public void saveModel(CreateModelRequest request) {

    }

    @Override
    public void updateModel(Integer id, UpdateModelRequest request) {

    }

    @Override
    public void deleteModelById(Integer id) {

    }

    ModelResponse buildModelResponse(Model model){
        return ModelResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .category(model.getCategory())
                .yearFrom(model.getYearFrom())
                .yearTo(model.getYearTo())
                .build();
    }

//    Model buildModel(CreateModelRequest request){
//        return Model.builder()
//                .name(request.name())
//                .category(request.category())
//                .yearFrom(request.yearFrom())
//                .yearTo(request.yearTo())
//                .brand()
//                .build();
//
//    }
}
