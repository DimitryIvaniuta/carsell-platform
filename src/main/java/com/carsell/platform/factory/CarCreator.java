package com.carsell.platform.factory;

import com.carsell.platform.dto.BaseCarRequest;
import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import com.carsell.platform.entity.User;

public interface CarCreator<T extends BaseCarRequest, C extends Car> {//<T>

    /**
     * Creates a new instance of the Car subtype based on the provided request and seller.
     */
    C createCar(T request, User seller);

    /**
     * Returns the supported CarType for this creator.
     */
    CarType getSupportedCarType();

    /**
     * Returns the Class object of T to perform runtime type checks.
     */
//    Class<T> getRequestType();

    /**
     * A helper method that safely casts a BaseCarRequest to the specific type T and then delegates to createCar.
     * If the cast fails, an IllegalArgumentException is thrown.
     *
     * @param request the base request (should be of type T)
     * @param seller  the seller associated with the car
     * @return a new instance of the Car subtype C
     */
/*
    default C createCarSafe(BaseCarRequest request, User seller) {
        if (!getRequestType().isInstance(request)) {
            throw new IllegalArgumentException("Invalid request type for car type: "
                    + getSupportedCarType() + ". Expected: "
                    + getRequestType().getName() + " but got: "
                    + request.getClass().getName());
        }
        T castedRequest = getRequestType().cast(request);
        return createCar(castedRequest, seller);
    }
*/

}
