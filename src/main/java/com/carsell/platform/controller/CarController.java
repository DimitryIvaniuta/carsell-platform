package com.carsell.platform.controller;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * Retrieve a list of all cars.
     *
     * @return a ResponseEntity containing a list of CarResponse DTOs
     */
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> responses = carService.getAllCars();
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieve a car by its identifier.
     *
     * @param id the identifier of the car
     * @return a ResponseEntity containing the CarResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        CarResponse response = carService.getCarById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new car from the provided request.
     *
     * @param request the car creation request DTO
     * @return a ResponseEntity containing the created CarResponse DTO
     */
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody BaseCarRequest request) {
        CarResponse response = carService.createCar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing car with the provided request.
     *
     * @param id      the identifier of the car to update
     * @param request the car update request DTO
     * @return a ResponseEntity containing the updated CarResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @Valid @RequestBody BaseCarRequest request) {
        CarResponse response = carService.updateCar(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an existing car by its identifier.
     *
     * @param id the identifier of the car to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
