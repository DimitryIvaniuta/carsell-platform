package com.carsell.platform.factory;

import com.carsell.platform.entity.Car;
import com.carsell.platform.entity.CarType;
import jakarta.inject.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface CarCreatorQualifier {

    CarType value() default CarType.EMPTY;

}
