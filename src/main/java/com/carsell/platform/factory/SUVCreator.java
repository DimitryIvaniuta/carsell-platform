package com.carsell.platform.factory;

import com.carsell.platform.dto.SUVCarRequest;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.SUV;
import com.carsell.platform.entity.User;
import org.springframework.stereotype.Component;

@CarCreatorQualifier(CarType.SUV)
@Component
public class SUVCreator implements CarCreator<SUVCarRequest, SUV> {

    @Override
    public CarType getSupportedCarType() {
        return CarType.SUV;
    }

    @Override
    public SUV createCar(SUVCarRequest request, User seller) {
        return SUV.builder()
                .fourWheelDrive(request.getFourWheelDrive())
                .seller(seller)
                .build();
    }
}
