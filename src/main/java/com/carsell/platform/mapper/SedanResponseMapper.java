package com.carsell.platform.mapper;

import com.carsell.platform.dto.CarResponse;
import com.carsell.platform.dto.SedanCarResponse;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Sedan;
import org.springframework.stereotype.Component;

@Component
public class SedanResponseMapper extends AbstractCarResponseMapper<Sedan> {

    @Override
    public CarResponse toResponse(Car car) {
        Sedan sedan = (Sedan) car;
        return SedanCarResponse.builder()
                .base(buildCommonResponse(sedan))
                .trunkCapacity(sedan.getTrunkCapacity())
                .build();
    }

    @Override
    public CarType getSupportedCarType() {
        return CarType.SEDAN;
    }

}