package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateModelRequest;
import com.abbtech.dto.request.UpdateModelRequest;
import com.abbtech.dto.response.ModelResponse;
import com.abbtech.exception.CarException;
import com.abbtech.exception.ModelException;
import com.abbtech.model.Brand;
import com.abbtech.model.Model;
import com.abbtech.repository.ModelRepository;
import com.abbtech.service.BrandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceImplTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private BrandService brandService;

    @InjectMocks
    private ModelServiceImpl modelService;

    @Test
    void findById_success() {
        Model model = Model.builder().id(1).name("Corolla").category("Sedan").build();
        when(modelRepository.findById(1)).thenReturn(Optional.of(model));

        Model actual = modelService.findById(1);

        Assertions.assertEquals("Corolla", actual.getName());
        Assertions.assertEquals("Sedan", actual.getCategory());
    }

    @Test
    void findById_notFound() {
        when(modelRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(ModelException.class, () -> modelService.findById(10));
    }

    @Test
    void getModelById_success() {
        Model model = Model.builder().id(1).name("Corolla").category("Sedan").build();
        when(modelRepository.findById(1)).thenReturn(Optional.of(model));

        ModelResponse response = modelService.getModelById(1);

        Assertions.assertEquals(1, response.id());
        Assertions.assertEquals("Corolla", response.name());
        Assertions.assertEquals("Sedan", response.category());
    }

    @Test
    void getAllModels_success() {
        Model model1 = Model.builder().id(1).name("Corolla").category("Sedan").build();
        Model model2 = Model.builder().id(2).name("Rav4").category("SUV").build();
        when(modelRepository.findAll()).thenReturn(List.of(model1, model2));

        List<ModelResponse> actual = modelService.getAllModels();

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Corolla", actual.get(0).name());
        Assertions.assertEquals("Rav4", actual.get(1).name());
    }

    @Test
    void saveModel_success() {
        Brand brand = Brand.builder().id(1).name("Toyota").build();
        CreateModelRequest request = new CreateModelRequest("Corolla", "Sedan", 2000, 2023);

        when(brandService.getBrandById(1)).thenReturn(brand);

        Assertions.assertDoesNotThrow(() -> modelService.saveModel(1, request));
        verify(modelRepository, times(1)).save(any(Model.class));
    }

    @Test
    void saveModel_brandNotFound() {
        CreateModelRequest request = new CreateModelRequest("Corolla", "Sedan", 2000, 2023);
        when(brandService.getBrandById(1)).thenReturn(null);

        Assertions.assertThrows(CarException.class, () -> modelService.saveModel(1, request));
        verify(modelRepository, never()).save(any());
    }

    @Test
    void updateModel_success() {
        Model model = Model.builder().id(1).name("Corolla").category("Sedan").yearFrom(2000).yearTo(2020).build();
        UpdateModelRequest request = new UpdateModelRequest("Corolla Updated", "Hatchback", 2005, 2022);

        when(modelRepository.findById(1)).thenReturn(Optional.of(model));

        Assertions.assertDoesNotThrow(() -> modelService.updateModel(1, request));

        Assertions.assertEquals("Corolla Updated", model.getName());
        Assertions.assertEquals("Hatchback", model.getCategory());
        Assertions.assertEquals(2005, model.getYearFrom());
        Assertions.assertEquals(2022, model.getYearTo());
        verify(modelRepository, times(1)).save(model);
    }

    @Test
    void updateModel_notFound() {
        UpdateModelRequest request = new UpdateModelRequest("Corolla Updated", "Hatchback", 2005, 2022);
        when(modelRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(ModelException.class, () -> modelService.updateModel(10, request));
        verify(modelRepository, never()).save(any());
    }

    @Test
    void deleteModelById_success() {
        Model model = Model.builder().id(1).name("Corolla").category("Sedan").build();
        when(modelRepository.findById(1)).thenReturn(Optional.of(model));

        Assertions.assertDoesNotThrow(() -> modelService.deleteModelById(1));
        verify(modelRepository, times(1)).delete(model);
    }

    @Test
    void deleteModelById_notFound() {
        when(modelRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(ModelException.class, () -> modelService.deleteModelById(10));
        verify(modelRepository, never()).delete(any());
    }
    }