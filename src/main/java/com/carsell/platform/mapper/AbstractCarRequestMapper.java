package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.entity.Car;

public abstract class AbstractCarRequestMapper implements CarRequestMapper {

    public Car mapCommonFields(BaseCarRequest request, Car entity) {
        entity.setMake(request.getMake());
        entity.setModel(request.getModel());
        entity.setYear(request.getYear());
        entity.setPrice(request.getPrice());
        entity.setDescription(request.getDescription());
        return entity;
    }

}
