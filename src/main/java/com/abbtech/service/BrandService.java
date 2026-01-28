package com.abbtech.service;

import com.abbtech.dto.UpdateBrandRequest;
import com.abbtech.dto.response.BrandResponse;
import com.abbtech.dto.request.CreateCarRequest;

import java.util.List;

public interface BrandService {

    List<BrandResponse> getBrands();

    BrandResponse getBrandById(int id);

    void addBrand(CreateCarRequest request);

    void deleteBrandById(int id);

    void updateBrand(int id, UpdateBrandRequest carDto);
}
