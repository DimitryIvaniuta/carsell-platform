package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;

public sealed interface CarResponse permits SedanCarResponse, SUVCarResponse, TruckCarResponse {

    BaseCarResponse base();

    CarType carType();

}
