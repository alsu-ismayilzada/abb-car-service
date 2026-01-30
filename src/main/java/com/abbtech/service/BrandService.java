package com.abbtech.service;

import com.abbtech.dto.request.UpdateBrandRequest;
import com.abbtech.dto.response.BrandResponse;
import com.abbtech.dto.request.CreateBrandRequest;
import com.abbtech.model.Brand;

import java.util.List;

public interface BrandService {

    Brand getBrandById(int id);

    List<BrandResponse> getBrands();

    BrandResponse getBrandResponseById(int id);

    void addBrand(CreateBrandRequest request);

    void deleteBrandById(int id);

    void updateBrand(int id, UpdateBrandRequest carDto);
}
