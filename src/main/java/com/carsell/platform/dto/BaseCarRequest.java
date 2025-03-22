package com.carsell.platform.dto;

import com.carsell.platform.entity.CarType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SedanCarRequest.class, name = CarType.SEDAN_TYPE),
        @JsonSubTypes.Type(value = SUVCarRequest.class, name = CarType.SUV_TYPE),
        @JsonSubTypes.Type(value = TruckCarRequest.class, name = CarType.TRUCK_TYPE)
})
@NoArgsConstructor
@ToString
public abstract class BaseCarRequest {


    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Seller id is required")
    private Long sellerId;

    private String description;

    /**
     * Returns the CarType for this request.
     */
    @JsonIgnore
    public abstract CarType getSupportedCarType();

}
