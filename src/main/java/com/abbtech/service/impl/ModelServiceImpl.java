package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateModelRequest;
import com.abbtech.dto.request.UpdateModelRequest;
import com.abbtech.dto.response.ModelResponse;
import com.abbtech.exception.CarErrorEnum;
import com.abbtech.exception.CarException;
import com.abbtech.exception.ModelErrorEnum;
import com.abbtech.exception.ModelException;
import com.abbtech.model.Brand;
import com.abbtech.model.Model;
import com.abbtech.repository.ModelRepository;
import com.abbtech.service.BrandService;
import com.abbtech.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandService brandService;

    @Override
    public Model findById(Integer id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ModelException(ModelErrorEnum.MODEL_NOT_FOUND));
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
    public void saveModel(Integer brandId, CreateModelRequest request) {
        var brand = brandService.getBrandById(brandId);
        if (brand == null) {
            throw new CarException(CarErrorEnum.BRAND_NOT_FOUND);
        }
        var model = buildModel(request, brand);
        modelRepository.save(model);
    }

    @Override
    public void updateModel(Integer id, UpdateModelRequest request) {
        var model = findById(id);
        model.setName(request.name());
        model.setCategory(request.category());
        model.setYearFrom(request.yearFrom());
        model.setYearTo(request.yearTo());
        modelRepository.save(model);
    }

    @Override
    public void deleteModelById(Integer id) {
        var model = findById(id);
        modelRepository.delete(model);
    }

    private ModelResponse buildModelResponse(Model model){
        return ModelResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .category(model.getCategory())
                .yearFrom(model.getYearFrom())
                .yearTo(model.getYearTo())
                .build();
    }

    private Model buildModel(CreateModelRequest request, Brand brand){
        return Model.builder()
                .name(request.name())
                .category(request.category())
                .yearFrom(request.yearFrom())
                .yearTo(request.yearTo())
                .brand(brand)
                .build();

    }
}
