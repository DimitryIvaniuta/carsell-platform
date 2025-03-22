package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import lombok.Builder;

@Builder
public record SUVCarResponse(
        BaseCarResponse base,
        Boolean fourWheelDrive  // SUV-specific field
) implements CarResponse {

    @Override
    public CarType carType() {
        return CarType.SUV;
    }

}
