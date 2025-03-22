package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import lombok.Builder;

@Builder
public record TruckCarResponse(
        BaseCarResponse base,
        Double payloadCapacity  // Truck-specific field
) implements CarResponse {

    @Override
    public CarType carType() {
        return CarType.TRUCK;
    }

}