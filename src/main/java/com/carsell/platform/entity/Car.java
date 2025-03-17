package com.carsell.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="car_type", discriminatorType = DiscriminatorType.INTEGER)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Car {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_UNIQUE_ID")
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="make", nullable = false)
    private String make;

    @Column(name="model", nullable = false)
    private String model;

    @Column(name="year")
    private int year;

    @Column(name="color")
    private String color;

    @Column(name="engine")
    private String engine;

    @Column(name="fuel")
    private String fuel;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable = false)
    private User seller;

}
