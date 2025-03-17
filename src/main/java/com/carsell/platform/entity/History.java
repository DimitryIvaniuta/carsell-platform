package com.carsell.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Embeddable
@Data
@NoArgsConstructor
public class History {

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private ZonedDateTime updatedAt;

}
