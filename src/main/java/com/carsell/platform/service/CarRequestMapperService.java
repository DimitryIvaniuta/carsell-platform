package com.carsell.platform.service;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.exception.ResourceNotFoundException;
import com.carsell.platform.mapper.CarRequestMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CarRequestMapperService {

    // Inject all CarRequestMapper beans.
    private final List<CarRequestMapper> requestMappers;

    // Map keyed by Car.CarType.
    private Map<CarType, CarRequestMapper> requestMapperMap;

    public CarRequestMapperService(List<CarRequestMapper> requestMappers) {
        this.requestMappers = requestMappers;
    }

    @PostConstruct
    public void init() {
        requestMapperMap = requestMappers.stream()
                .collect(Collectors.toMap(
                        CarRequestMapper::getSupportedCarType,
                        Function.identity()
                ));
    }

    /**
     * Converts any Car entity into its corresponding CarRequest DTO
     * by delegating to the appropriate mapper based on car.getCarType().
     */
    public <R extends BaseCarRequest> Car toEntity(R request) {
        CarRequestMapper mapper = requestMapperMap.get(request.getSupportedCarType());
        if (mapper == null) {
            throw new ResourceNotFoundException(String.format("No mapper found for car type: %s", request.getSupportedCarType()));
        }
        return mapper.toEntity(request);
    }

    public <T extends Car, R extends BaseCarRequest> void updateEntity(T car, R request) {
        CarRequestMapper mapper = requestMapperMap.get(car.getCarType());
        if (mapper == null) {
            throw new ResourceNotFoundException(String.format("No mapper found for car type to update: %s", car.getCarType()));
        }
        mapper.updateEntity(request, car);
    }

}
