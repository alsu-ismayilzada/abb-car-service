package com.abbtech.service.impl;

import com.abbtech.dto.ModelDto;
import com.abbtech.dto.UpdateBrandRequest;
import com.abbtech.dto.response.BrandResponse;
import com.abbtech.dto.request.CreateBrandRequest;
import com.abbtech.exception.CarErrorEnum;
import com.abbtech.exception.CarException;
import com.abbtech.model.Brand;
import com.abbtech.model.Model;
import com.abbtech.repository.BrandRepository;
import com.abbtech.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand getBrandById(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(BrandServiceImpl::buildBrand)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandResponseById(int id) {
        var brand = getBrandById(id);
        return buildBrand(brand);
    }

    @Override
    @Transactional
    public void addBrand(CreateBrandRequest request) {
        var brand = Brand.builder()
                .name(request.name())
                .country(request.country())
                .foundedYear(request.foundedYear())
                .build();
        var models = request.models().stream()
                .map(modelDto -> Model.builder()
                        .brand(brand)
                        .category(modelDto.category())
                        .name(modelDto.name())
                        .yearFrom(modelDto.yearFrom())
                        .yearTo(modelDto.yearTo())
                        .build()).toList();
        brand.setModels(models);
        brandRepository.save(brand);
    }

    @Override
    @Transactional
    public void deleteBrandById(int id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));

        brandRepository.delete(brand);
    }


    @Override
    @Transactional
    public void updateBrand(int id, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));
        brand.setName(request.name());
        brand.setCountry(request.country());
        brandRepository.save(brand);
    }

    private static BrandResponse buildBrand(Brand brand) {
        List<ModelDto> models = brand.getModels() == null
                ? List.of()
                : brand.getModels().stream()
                .filter(Objects::nonNull)
                .map(model -> new ModelDto(
                        model.getId(),
                        model.getName(),
                        model.getCategory(),
                        model.getYearFrom(),
                        model.getYearTo()
                ))
                .toList();

        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getCountry(),
                models
        );
    }


}