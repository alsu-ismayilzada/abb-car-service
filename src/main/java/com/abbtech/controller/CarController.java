package com.abbtech.controller;

import com.abbtech.dto.request.CreateCarRequest;
import com.abbtech.dto.request.UpdateCarRequest;
import com.abbtech.dto.response.CarResponse;
import com.abbtech.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping
    public ResponseEntity<Void> saveCar(@RequestBody @Valid CreateCarRequest request) {
        carService.saveCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateCarRequest request) {

        carService.updateCar(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Integer id) {
        carService.deleteCarById(id);
        return ResponseEntity.noContent().build();
    }
}
