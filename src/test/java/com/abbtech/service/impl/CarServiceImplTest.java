package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateCarDetailsRequest;
import com.abbtech.dto.request.CreateCarRequest;
import com.abbtech.dto.request.UpdateCarDetailsRequest;
import com.abbtech.dto.request.UpdateCarRequest;
import com.abbtech.dto.response.CarResponse;
import com.abbtech.model.Car;
import com.abbtech.model.CarDetails;
import com.abbtech.model.Feature;
import com.abbtech.model.Model;
import com.abbtech.repository.CarRepository;
import com.abbtech.service.FeatureService;
import com.abbtech.service.ModelService;
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
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelService modelService;

    @Mock
    private FeatureService featureService;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void getCarById_success() {
        Model model = Model.builder().id(1).name("Corolla").build();
        CarDetails carDetails = CarDetails.builder().id(1).color("Red").build();
        Car car = Car.builder().id(1).vin("VIN123").registrationNumber("REG123")
                .mileageKm(10000).productionYear(2020).model(model)
                .carDetails(carDetails).features(List.of()).build();

        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        CarResponse response = carService.getCarById(1);

        Assertions.assertEquals("VIN123", response.vin());
        Assertions.assertEquals(1, response.modelId());
        Assertions.assertEquals("Red", response.carDetails().color());
    }

    @Test
    void getAllCars_success() {
        Model model = Model.builder().id(1).name("Corolla").build();
        CarDetails carDetails1 = CarDetails.builder().id(1).color("Red").build();
        CarDetails carDetails2 = CarDetails.builder().id(2).color("Blue").build();

        Car car1 = Car.builder().id(1).vin("VIN123").model(model).carDetails(carDetails1).features(List.of()).build();
        Car car2 = Car.builder().id(2).vin("VIN456").model(model).carDetails(carDetails2).features(List.of()).build();

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        List<CarResponse> responseList = carService.getAllCars();

        Assertions.assertEquals(2, responseList.size());
        Assertions.assertEquals("VIN123", responseList.get(0).vin());
        Assertions.assertEquals("VIN456", responseList.get(1).vin());
    }

    @Test
    void saveCar_success() {
        Model model = Model.builder().id(1).name("Corolla").build();
        when(modelService.findById(1)).thenReturn(model);
        when(featureService.findAllById(List.of(1,2))).thenReturn(List.of(
                Feature.builder().id(1).name("Feature1").build(),
                Feature.builder().id(2).name("Feature2").build()
        ));

        CreateCarDetailsRequest detailsRequest = new CreateCarDetailsRequest("Red", "AZ-REG-2024-556", "ENG123", "Petrol", "INS123", "REGCODE123");
        CreateCarRequest request = new CreateCarRequest("1HGCM82633A123456", "VIN123", 124, 10000, 1, detailsRequest, List.of(1,2));

        Assertions.assertDoesNotThrow(() -> carService.saveCar(request));

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void updateCar_success() {
        Model oldModel = Model.builder().id(1).name("Corolla").build();
        Model newModel = Model.builder().id(2).name("Camry").build();

        CarDetails carDetails = CarDetails.builder().id(1).color("Red").build();
        Car car = Car.builder().id(1).vin("VIN123").registrationNumber("REG123").mileageKm(10000)
                .productionYear(2020).model(oldModel).carDetails(carDetails).build();

        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        when(modelService.findById(2)).thenReturn(newModel);

        UpdateCarDetailsRequest detailsRequest = new UpdateCarDetailsRequest("ENG456", "AZ-REG-2024-556", "Blue", "INS123", "Petr12", "REGCODE123");
        UpdateCarRequest updateRequest = new UpdateCarRequest("HGCM82633A123456", "REG456", 20000, 2022, 2, detailsRequest, List.of(1,2));

        Assertions.assertDoesNotThrow(() -> carService.updateCar(1, updateRequest));

        Assertions.assertEquals("HGCM82633A123456", car.getVin());
        Assertions.assertEquals("REG456", car.getRegistrationNumber());
        Assertions.assertEquals(20000, car.getMileageKm());
        Assertions.assertEquals(2022, car.getProductionYear());
        Assertions.assertEquals("Blue", car.getCarDetails().getColor());
        Assertions.assertEquals("ENG456", car.getCarDetails().getEngineNumber());
        Assertions.assertEquals(newModel, car.getModel());

        verify(carRepository, times(1)).save(car);
    }

    @Test
    void deleteCarById_success() {
        Car car = Car.builder().id(1).vin("VIN123").build();
        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        Assertions.assertDoesNotThrow(() -> carService.deleteCarById(1));

        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void getCarById_notFound() {
        when(carRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> carService.getCarById(10));
    }

    @Test
    void deleteCarById_notFound() {
        when(carRepository.findById(10)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> carService.deleteCarById(10));

        verify(carRepository, never()).delete(any());
    }
}