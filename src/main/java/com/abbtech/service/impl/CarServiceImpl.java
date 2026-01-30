package com.abbtech.service.impl;

import com.abbtech.dto.request.CreateCarRequest;
import com.abbtech.dto.request.UpdateCarRequest;
import com.abbtech.dto.response.CarDetailsResponse;
import com.abbtech.dto.response.CarResponse;
import com.abbtech.dto.response.FeatureResponse;
import com.abbtech.model.Car;
import com.abbtech.model.CarDetails;
import com.abbtech.model.Feature;
import com.abbtech.model.Model;
import com.abbtech.repository.CarRepository;
import com.abbtech.service.CarService;
import com.abbtech.service.FeatureService;
import com.abbtech.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelService modelService;
    private final FeatureService featureService;

    private Car findById(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCarById(Integer id) {
        var car = findById(id);
        return buildCarResponse(car);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::buildCarResponse)
                .toList();
    }

    @Override
    @Transactional
    public void saveCar(CreateCarRequest request) {
        var carDetails = buildCarDetails(request);
        var car = buildCar(request, carDetails);
        if (request.featureIds() != null && !request.featureIds().isEmpty()) {
            List<Feature> features =
                    featureService.findAllById(request.featureIds());
            car.setFeatures(features);
        }
        carDetails.setCar(car);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void updateCar(Integer id, UpdateCarRequest request) {
        var car = findById(id);
        car.setModel(modelService.findById(request.modelId()));
        car.setVin(request.vin());
        car.setRegistrationNumber(request.registrationNumber());
        car.setMileageKm(request.mileageKm());
        car.setProductionYear(request.productionYear());

        var carDetails = car.getCarDetails();
        carDetails.setColor(request.carDetails().color());
        carDetails.setEngineCapacity(request.carDetails().engineCapacity());
        carDetails.setEngineNumber(request.carDetails().engineNumber());
        carDetails.setFuelType(request.carDetails().fuelType());
        carDetails.setInsuranceNumber(request.carDetails().insuranceNumber());
        carDetails.setRegistrationCode(request.carDetails().registrationCode());
        car.setCarDetails(carDetails);

        carRepository.save(car);
    }

    @Override
    public void deleteCarById(Integer id) {
        var car = findById(id);
        carRepository.delete(car);
    }


    CarResponse buildCarResponse(Car car){
        return CarResponse.builder()
                .id(car.getId())
                .vin(car.getVin())
                .registrationNumber(car.getRegistrationNumber())
                .mileageKm(car.getMileageKm())
                .productionYear(car.getProductionYear())
                .modelId(car.getModel().getId())
                .features(car.getFeatures().stream()
                        .map(this::buildFeatureResponse)
                        .toList())
                .carDetails(buildCarDetailsResponse(car.getCarDetails()))
                .build();
    }

    Car buildCar(CreateCarRequest request, CarDetails carDetails){

        return Car.builder()
                .vin(request.vin())
                .registrationNumber(request.registrationNumber())
                .mileageKm(request.mileageKm())
                .productionYear(request.productionYear())
                .model(modelService.findById(request.modelId()))
                .carDetails(carDetails)
                .build();
    }

    CarDetails buildCarDetails(CreateCarRequest request){
        var carDetailsRequest = request.carDetails();
        return CarDetails.builder()
                .color(carDetailsRequest.color())
                .engineCapacity(carDetailsRequest.engineCapacity())
                .engineNumber(carDetailsRequest.engineNumber())
                .fuelType(carDetailsRequest.fuelType())
                .insuranceNumber(carDetailsRequest.insuranceNumber())
                .registrationCode(carDetailsRequest.registrationCode())
                .build();
    }

    CarDetailsResponse buildCarDetailsResponse(CarDetails carDetails){
        return CarDetailsResponse.builder()
                .id(carDetails.getId())
                .color(carDetails.getColor())
                .engineCapacity(carDetails.getEngineCapacity())
                .engineNumber(carDetails.getEngineNumber())
                .fuelType(carDetails.getFuelType())
                .insuranceNumber(carDetails.getInsuranceNumber())
                .registrationCode(carDetails.getRegistrationCode())
                .build();
    }

    FeatureResponse buildFeatureResponse(Feature feature){
        return FeatureResponse.builder()
                .id(feature.getId())
                .featureId(feature.getFeatureId())
                .name(feature.getName())
                .description(feature.getDescription())
                .category(feature.getCategory())
                .build();
    }
}
