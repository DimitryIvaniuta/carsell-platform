package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class TruckCarRequest extends BaseCarRequest {

    @NotNull(message = "Payload capacity is required for a truck")
    private final Double payloadCapacity;

    @Override
    public CarType getSupportedCarType() {
        return CarType.TRUCK;
    }

}
