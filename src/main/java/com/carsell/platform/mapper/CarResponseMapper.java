package com.carsell.platform.mapper;

import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;

public interface CarResponseMapper {

    /**
     * Converts a Car entity into its corresponding CarResponse DTO.
     */
    CarResponse toResponse(Car car);

    /**
     * Returns the CarType that this mapper supports.
     */
    CarType getSupportedCarType();

}
