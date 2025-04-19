package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BaseCarResponse(
        Long id,
        CarType carType,
        String make,
        String model,
        int year,
        BigDecimal price,
        String description
) {
}
