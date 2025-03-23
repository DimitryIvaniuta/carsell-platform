package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SedanCarRequest extends BaseCarRequest {

    @NotNull(message = "Trunk capacity is required for a sedan")
    private Double trunkCapacity;

    @Override
    public CarType getSupportedCarType() {
        return CarType.SEDAN;
    }

}
