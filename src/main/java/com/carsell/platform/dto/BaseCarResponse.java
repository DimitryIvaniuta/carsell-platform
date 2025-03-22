package com.carsell.platform.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BaseCarResponse(
        Long id,
        String make,
        String model,
        int year,
        BigDecimal price,
        String description
) {}