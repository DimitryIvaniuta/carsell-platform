package com.carsell.platform.service;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.User;
import com.carsell.platform.exception.ResourceNotFoundException;
import com.carsell.platform.repository.CarRepository;
import com.carsell.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceHandler implements CarService {

    private static final String NOT_FOUND_MESSAGE = "Car not found %s";
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarResponseMapperService carResponseMapperService;
    private final CarRequestMapperService carRequestMapperService;

    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars() {
        Iterable<Car> cars = carRepository.findAll();
        return StreamSupport.stream(cars.spliterator(), false)
                .map(carResponseMapperService::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
        return carResponseMapperService.toResponse(car);
    }

    @Override
    @Transactional
    public CarResponse createCar(BaseCarRequest request) {
        // Retrieve the seller by its ID.
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + request.getSellerId()));

        // Use CarMapper to convert the request to a new Car entity.
        Car car = carRequestMapperService.toEntity(request);
        car.setSeller(seller); // Ensure the seller is set.

        // Persist the new Car.
        Car savedCar = carRepository.save(car);
        log.info("Created new car with id: {}", savedCar.getId());

        // Convert the saved Car entity into a response DTO.
        return carResponseMapperService.toResponse(savedCar);
    }

    @Override
    @Transactional
    public CarResponse updateCar(Long id, BaseCarRequest request) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, id)));

        // Use CarMapper to update the existing entity from the request.
        carRequestMapperService.updateEntity(existingCar, request);

        // Persist changes.
        Car updatedCar = carRepository.save(existingCar);
        log.info("Updated car with id: {}", updatedCar.getId());

        // Map the updated entity to a response DTO.
        return carResponseMapperService.toResponse(updatedCar);
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
        log.info("Deleted car with id: {}", id);
    }
}
