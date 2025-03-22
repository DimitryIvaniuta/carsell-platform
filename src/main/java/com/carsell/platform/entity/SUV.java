package com.carsell.platform.entity;

import com.carsell.platform.dto.SUVCarRequest;
import com.carsell.platform.dto.TruckCarRequest;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.carsell.platform.entity.CarType.SUV_TYPE;


@Entity
@DiscriminatorValue(SUV_TYPE)
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SUV extends Car {//<SUVCarRequest>

    @Column(name="four_wheel_drive")
    @Builder.Default
    private Boolean fourWheelDrive = false;

    @Override
    public CarType getCarType() {
        return CarType.SUV;
    }

/*
    @Override
    public void updateSpecificFields(SUVCarRequest request) {
        this.setFourWheelDrive(request.getFourWheelDrive());
    }
*/

}
