package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SUVCarRequest extends BaseCarRequest {

    @NotNull(message = "Four wheel drive indicator is required for an SUV")
    private Boolean fourWheelDrive;

    @Override
    public CarType getSupportedCarType() {
        return CarType.SUV;
    }

}