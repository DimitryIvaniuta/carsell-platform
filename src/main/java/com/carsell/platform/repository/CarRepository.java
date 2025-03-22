package com.carsell.platform.repository;

import com.carsell.platform.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
    //
}
