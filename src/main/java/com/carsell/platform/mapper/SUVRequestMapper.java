package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.SUVCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.SUV;
import org.springframework.stereotype.Component;

@Component
public class SUVRequestMapper extends AbstractCarRequestMapper {

    /**
     * Creates a new Car entity from a request DTO.
     */
    public Car toEntity(BaseCarRequest request) {
        final SUVCarRequest suvCarRequest = (SUVCarRequest) request;
        return SUV.builder()
                .fourWheelDrive(suvCarRequest.getFourWheelDrive())
                .build();
    }

    /**
     * Updates an existing Car entity with values from the request DTO.
     */
    public void updateEntity(BaseCarRequest request, Car entity) {
        final SUV suv = (SUV) mapCommonFields(request, entity);
        final SUVCarRequest suvCarRequest = (SUVCarRequest) request;
        suv.setFourWheelDrive(suvCarRequest.getFourWheelDrive());
    }

    /**
     * Returns the CarType that this mapper supports.
     */
    public CarType getSupportedCarType() {
        return CarType.SUV;
    }

}
