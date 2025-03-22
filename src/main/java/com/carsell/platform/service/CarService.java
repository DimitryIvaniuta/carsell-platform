package com.carsell.platform.service;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.CarResponse;

import java.util.List;

public interface CarService {

    List<CarResponse> getAllCars();

    CarResponse getCarById(Long id);

    CarResponse createCar(BaseCarRequest request);

    CarResponse updateCar(Long id, BaseCarRequest request);

    void deleteCar(Long id);

}