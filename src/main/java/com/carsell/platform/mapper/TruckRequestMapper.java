package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.TruckCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckRequestMapper extends AbstractCarRequestMapper {

    /**
     * Creates a new Car entity from a request DTO.
     */
    public Car toEntity(BaseCarRequest request) {
        final TruckCarRequest truckCarRequest = (TruckCarRequest) request;
        return Truck.builder()
                .payloadCapacity(truckCarRequest.getPayloadCapacity())
                .build();
    }

    /**
     * Updates an existing Car entity with values from the request DTO.
     */
    public void updateEntity(BaseCarRequest request, Car entity) {
        final Truck truck = (Truck) mapCommonFields(request, entity);
        final TruckCarRequest truckCarRequest = (TruckCarRequest) request;
        truck.setPayloadCapacity(truckCarRequest.getPayloadCapacity());
    }

    /**
     * Returns the CarType that this mapper supports.
     */
    public CarType getSupportedCarType() {
        return CarType.TRUCK;
    }

}
