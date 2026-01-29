package com.abbtech.service;

import com.abbtech.dto.request.CreateModelRequest;
import com.abbtech.dto.request.UpdateModelRequest;
import com.abbtech.dto.response.ModelResponse;
import com.abbtech.model.Model;

import java.util.List;

public interface ModelService {

    Model findById(Integer id);
    ModelResponse getModelById(Integer id);
    List<ModelResponse> getAllModels();
    void saveModel(CreateModelRequest request);
    void updateModel(Integer id, UpdateModelRequest request);
    void deleteModelById(Integer id);

}
