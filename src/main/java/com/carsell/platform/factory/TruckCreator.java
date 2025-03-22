package com.carsell.platform.factory;

import com.carsell.platform.dto.TruckCarRequest;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Truck;
import com.carsell.platform.entity.User;
import org.springframework.stereotype.Component;

@Component
@CarCreatorQualifier(CarType.TRUCK)
public class TruckCreator implements CarCreator<TruckCarRequest, Truck> {

    @Override
    public CarType getSupportedCarType() {
        return CarType.TRUCK;
    }

    @Override
    public Truck createCar(TruckCarRequest request, User seller) {
        return Truck.builder()
                .payloadCapacity(request.getPayloadCapacity())
                .seller(seller)
                .build();
    }

}
