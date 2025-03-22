package com.carsell.platform.mapper;

import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.dto.SUVCarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.SUV;
import org.springframework.stereotype.Component;

@Component
public class SUVResponseMapper extends AbstractCarResponseMapper<SUV> {

    @Override
    public CarResponse toResponse(Car car) {
        SUV suv = (SUV) car;
        return SUVCarResponse.builder()
                .base(buildCommonResponse(suv))
                .fourWheelDrive(suv.getFourWheelDrive())
                .build();
    }

    @Override
    public CarType getSupportedCarType() {
        return CarType.SUV;
    }

}
