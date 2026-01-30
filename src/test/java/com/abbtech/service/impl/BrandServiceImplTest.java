package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateBrandRequest;
import com.abbtech.dto.request.UpdateBrandRequest;
import com.abbtech.dto.response.BrandResponse;
import com.abbtech.exception.CarException;
import com.abbtech.model.Brand;
import com.abbtech.repository.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    void getBrandById_success() {
        Brand brand = Brand.builder()
                .id(1)
                .name("Toyota")
                .country("Japan")
                .build();

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Brand actual = brandService.getBrandById(1);

        Assertions.assertEquals("Toyota", actual.getName());
        Assertions.assertEquals("Japan", actual.getCountry());
    }

    @Test
    void getBrandById_notFound() {
        when(brandRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(CarException.class,
                () -> brandService.getBrandById(anyInt()));
    }

    @Test
    void getBrands_success() {
        Brand brand1 = Brand.builder().id(1).name("Toyota").country("Japan").build();
        Brand brand2 = Brand.builder().id(2).name("BMW").country("Germany").build();

        when(brandRepository.findAll()).thenReturn(List.of(brand1, brand2));

        List<BrandResponse> actual = brandService.getBrands();

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Toyota", actual.get(0).name());
        Assertions.assertEquals("BMW", actual.get(1).name());
    }

    @Test
    void getBrandResponseById_success() {
        Brand brand = Brand.builder()
                .id(1)
                .name("Toyota")
                .country("Japan")
                .build();

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        BrandResponse response = brandService.getBrandResponseById(1);

        Assertions.assertEquals(1, response.id());
        Assertions.assertEquals("Toyota", response.name());
        Assertions.assertEquals("Japan", response.country());
    }

    @Test
    void addBrand_success() {
        CreateBrandRequest request = new CreateBrandRequest(
                "Toyota",
                "Japan",
                1937,
                List.of() // models empty
        );

        Assertions.assertDoesNotThrow(() -> brandService.addBrand(request));

        verify(brandRepository, times(1)).save(any());
    }

    @Test
    void deleteBrandById_success() {
        Brand brand = Brand.builder().id(1).name("Toyota").country("Japan").build();

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Assertions.assertDoesNotThrow(() -> brandService.deleteBrandById(1));
        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    void deleteBrandById_notFound() {
        when(brandRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(CarException.class,
                () -> brandService.getBrandById(anyInt()));

        verify(brandRepository, never()).delete(any());
    }

    @Test
    void updateBrand_success() {
        Brand brand = Brand.builder().id(1).name("Toyota").country("Amerika").foundedYear(1945).build();
        UpdateBrandRequest request = UpdateBrandRequest.builder()
                .name("Toyota Updated")
                .country("Japan")
                .foundedYear(1940)
                .build();

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Assertions.assertDoesNotThrow(() -> brandService.updateBrand(1, request));

        Assertions.assertEquals("Toyota Updated", brand.getName());
        Assertions.assertEquals("Japan", brand.getCountry());
        Assertions.assertEquals(1940, brand.getFoundedYear());
        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void updateBrand_notFound() {
        UpdateBrandRequest request = UpdateBrandRequest.builder()
                .name("Toyota Updated")
                .country("Japan")
                .foundedYear(1940)
                .build();
        when(brandRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                CarException.class,
                () -> brandService.updateBrand(10, request)
        );

        verify(brandRepository, never()).save(any());
    }
}
