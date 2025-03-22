package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarResponse;
import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;

public abstract class AbstractCarResponseMapper<T extends Car> implements CarResponseMapper {

    /**
     * Builds the common part of the response DTO from the Car entity.
     */
    public BaseCarResponse buildCommonResponse(T car) {
        return BaseCarResponse.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .price(car.getPrice())
                .description(car.getDescription())
                .build();
    }

}
