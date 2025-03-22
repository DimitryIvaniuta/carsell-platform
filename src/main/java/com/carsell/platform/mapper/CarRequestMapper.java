package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;

public interface CarRequestMapper {

    /**
     * Creates a new Car entity from a request DTO.
     */
    Car toEntity(BaseCarRequest request);

    /**
     * Updates an existing Car entity with values from the request DTO.
     */
    void updateEntity(BaseCarRequest request, Car entity);

    /**
     * Returns the CarType that this mapper supports.
     */
    CarType getSupportedCarType();

}
