package com.carsell.platform.mapper;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.dto.SedanCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.Sedan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class SedanRequestMapper extends AbstractCarRequestMapper {

    /**
     * Creates a new Car entity from a request DTO.
     */
    public Car toEntity(BaseCarRequest request){
        log.info("Create car: {}", request);
        final SedanCarRequest sedanCarRequest = (SedanCarRequest)request;
        final Sedan sedan = Sedan.builder()
                .trunkCapacity(Optional.ofNullable(sedanCarRequest.getTrunkCapacity()).orElse(0.0))
                .build();
        return mapCommonFields(request, sedan);
    }

    /**
     * Updates an existing Car entity with values from the request DTO.
     */
    public void updateEntity(BaseCarRequest request, Car entity){
        log.info("Update car: {}", request);
        final Sedan sedan = (Sedan)mapCommonFields(request, entity);
        final SedanCarRequest sedanCarRequest = (SedanCarRequest)request;
        sedan.setTrunkCapacity(Optional.ofNullable(sedanCarRequest.getTrunkCapacity()).orElse(0.0));
    }

    /**
     * Returns the CarType that this mapper supports.
     */
    public CarType getSupportedCarType(){
        return CarType.SEDAN;
    }

}
