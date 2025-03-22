package com.carsell.platform.factory;

import com.carsell.platform.dto.SedanCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Sedan;
import com.carsell.platform.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@CarCreatorQualifier(CarType.SEDAN)
@Component
public class SedanCreator implements CarCreator<SedanCarRequest, Sedan> {

    @Override
    public CarType getSupportedCarType() {
        return CarType.SEDAN;
    }

    @Override
    public Sedan createCar(SedanCarRequest request, User seller) {
        return Sedan.builder()
                .trunkCapacity(Optional.ofNullable(request.getTrunkCapacity()).orElse(0.0))
                .seller(seller)
                .build();
    }

}
