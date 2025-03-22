package com.carsell.platform.entity;

import com.carsell.platform.dto.SedanCarRequest;
import com.carsell.platform.dto.TruckCarRequest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.carsell.platform.entity.CarType.TRUCK_TYPE;

@Entity
@DiscriminatorValue(TRUCK_TYPE)
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Truck extends Car {//<TruckCarRequest>

    private double payloadCapacity;

    @Override
    public CarType getCarType() {
        return CarType.TRUCK;
    }

/*
    @Override
    public void updateSpecificFields(TruckCarRequest request) {
        this.setPayloadCapacity(request.getPayloadCapacity());
    }
*/

}


