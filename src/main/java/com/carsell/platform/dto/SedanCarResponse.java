package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import lombok.Builder;

@Builder
public record SedanCarResponse(
        BaseCarResponse base,
        Double trunkCapacity  // Sedan-specific field
) implements CarResponse {

    @Override
    public CarType carType() {
        return CarType.SEDAN;
    }

}
