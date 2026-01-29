package com.abbtech.service;

import com.abbtech.dto.request.CreateCarRequest;
import com.abbtech.dto.request.UpdateCarRequest;
import com.abbtech.dto.response.CarResponse;

import java.util.List;

public interface CarService {

    CarResponse getCarById(Integer id);
    List<CarResponse> getAllCars();
    void saveCar(CreateCarRequest request);
    void updateCar(Integer id, UpdateCarRequest request);
    void deleteCarById(Integer id);

}
