package com.abbtech.controller;

import com.abbtech.dto.response.BrandResponse;
import com.abbtech.dto.UpdateBrandRequest;
import com.abbtech.dto.request.CreateBrandRequest;
import com.abbtech.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BrandResponse> getBrands() {
        return brandService.getBrands();
    }

    @GetMapping("/by-id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BrandResponse getBrandById(@RequestParam(value = "id") Integer id) {
        return brandService.getBrandResponseById(id);
    }

    @PostMapping
    public void addBrand(@RequestBody @Valid CreateBrandRequest reqBrandDto) {
        brandService.addBrand(reqBrandDto);
    }

    @PutMapping("/{brandId}")
    public void updateBrand(@PathVariable Integer brandId, @RequestBody @Valid UpdateBrandRequest request) {
        brandService.updateBrand(brandId, request);
    }

    @DeleteMapping("/{brandId}")
    public void deleteById(@PathVariable Integer brandId){
        brandService.deleteBrandById(brandId);
    }
}
