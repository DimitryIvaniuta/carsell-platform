package com.carsell.platform.entity;

import com.carsell.platform.dto.BaseCarRequest;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

@Entity
@Table(name = "cars")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "car_type", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@SuperBuilder
public abstract class Car {//<T extends BaseCarRequest>

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_UNIQUE_ID")
    @SequenceGenerator(name = "CS_UNIQUE_ID", sequenceName = "CS_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "manufacture_year")
    private Integer year;

    @Column(name = "color")
    private String color;

    @Column(name = "engine")
    private String engine;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    /**
     * Calculated discount amount using the price and discount percentage.
     * This field is computed by the database using the formula:
     * price * (discount_percentage / 100)
     */
/*    @Formula("price * (discount_percentage / 100)")
    private BigDecimal discountAmount;*/

/*    @Formula("EXTRACT(YEAR FROM CURRENT_DATE) - year")
    private Integer age;

    @Formula("CASE WHEN (EXTRACT(YEAR FROM CURRENT_DATE) - year) >= 25 THEN true ELSE false END")
    private Boolean vintage;*/

/*    private void updateCommonFields(T request) {
        this.setMake(request.getMake());
        this.setModel(request.getModel());
        this.setYear(request.getYear());
        this.setPrice(request.getPrice());
        this.setDescription(request.getDescription());
    }*/

    public abstract CarType getCarType();

//    public abstract void updateFromRequest(T request);

//    protected abstract void updateSpecificFields(T request);

    /*
        public final void updateFromRequest(T request) {
            updateCommonFields(request);
            updateSpecificFields(request);
        }
    */


    @Transient
    public BigDecimal getDiscountAmount() {
        if (price == null || discountPercentage == null) {
            return BigDecimal.ZERO;
        }
        return price
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Transient
    public Integer getAge() {
        return ZonedDateTime.now().getYear() - this.year;
    }

    @Transient
    public Boolean getVintage() {
        Integer age = getAge();
        return age != null && age >= 25;
    }

    public void applyDiscount(BigDecimal discountPercentage) {
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 ||
                discountPercentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.discountPercentage = discountPercentage;
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = newPrice;
    }

    public void changeOwner(User newOwner) {
        if (newOwner == null) {
            throw new IllegalArgumentException("New owner cannot be null");
        }
        this.seller = newOwner;
    }

    public void updateDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = newDescription;
    }



}
