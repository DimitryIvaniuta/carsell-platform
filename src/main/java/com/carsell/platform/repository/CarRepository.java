package com.carsell.platform.repository;

import com.carsell.platform.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findBySellerId(Long sellerId);

}
