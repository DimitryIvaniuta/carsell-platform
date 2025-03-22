package com.carsell.platform.mapper;

import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.dto.TruckCarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckResponseMapper extends AbstractCarResponseMapper<Truck> {

    @Override
    public CarResponse toResponse(Car car) {
        Truck truck = (Truck) car;
        return TruckCarResponse.builder()
                .base(buildCommonResponse(truck))
                .payloadCapacity(truck.getPayloadCapacity())
                .build();
    }

    @Override
    public CarType getSupportedCarType() {
        return CarType.TRUCK;
    }

}