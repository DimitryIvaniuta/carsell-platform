package com.carsell.platform.entity;

import com.carsell.platform.dto.SedanCarRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.carsell.platform.entity.CarType.SEDAN_TYPE;


@Entity
@DiscriminatorValue(SEDAN_TYPE)
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Sedan extends Car {//<SedanCarRequest>

    private double trunkCapacity;

    private int sedanCapacity = 2;

    @Override
    public CarType getCarType() {
        return CarType.SEDAN;
    }

/*
    @Override
    public void updateSpecificFields(SedanCarRequest request) {
        // Since updateFromRequest is invoked only if types match, we can safely cast.
        this.setTrunkCapacity(request.getTrunkCapacity());
    }
*/
}
