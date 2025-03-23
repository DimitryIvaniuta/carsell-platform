package com.carsell.platform.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String name;

    private String firstName;

    private String lastName;

    private String phone;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}
