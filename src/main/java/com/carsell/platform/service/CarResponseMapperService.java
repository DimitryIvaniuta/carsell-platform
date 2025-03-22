package com.carsell.platform.service;

import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.exception.ResourceNotFoundException;
import com.carsell.platform.mapper.CarResponseMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CarResponseMapperService {

    // Inject all CarResponseMapper beans.
    private final List<CarResponseMapper> responseMappers;

    // Map keyed by Car.CarType.
    private Map<CarType, CarResponseMapper> responseMapperMap;

    public CarResponseMapperService(List<CarResponseMapper> responseMappers) {
        this.responseMappers = responseMappers;
    }

    @PostConstruct
    public void init() {
        responseMapperMap = responseMappers.stream()
                .collect(Collectors.toMap(
                        CarResponseMapper::getSupportedCarType,
                        Function.identity()
                ));
    }

    /**
     * Converts any Car entity into its corresponding CarResponse DTO
     * by delegating to the appropriate mapper based on car.getCarType().
     */
    public <T extends Car> CarResponse toResponse(T car) {
        CarResponseMapper mapper = responseMapperMap.get(car.getCarType());
        if (mapper == null) {
            throw new ResourceNotFoundException(String.format("No mapper found for car type: %s", car.getCarType()));
        }
        return mapper.toResponse(car);
    }

}
