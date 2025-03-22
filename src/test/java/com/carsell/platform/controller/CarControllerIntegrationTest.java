package com.carsell.platform.controller;

import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.SedanCarRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.carsell.platform.entity.UserRole;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCarLifecycle() throws Exception {
        CreateUserRequest sellerRequest = CreateUserRequest.builder()
                .username("seller1")
                .login("seller1")
                .email("seller@example.com")
                .name("Car")
                .firstName("Car")
                .lastName("Seller")
                .password("securePass123")
                .roles(Set.of(UserRole.SELLER.name()))
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellerRequest)))
                .andExpect(status().isCreated());

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<UserRequest> request = new HttpEntity<>(sellerRequest, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("/api/users", request, String.class);


        // Ensure the cars list is initially empty.
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        // Create a new Sedan.
        SedanCarRequest sedanRequest = SedanCarRequest.builder()
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .price(BigDecimal.valueOf(25000))
                .description("A reliable sedan.")
                .sellerId(1L)
                .trunkCapacity(15.5)
                .build();

        String jsonRequest = objectMapper.writeValueAsString(sedanRequest);

        // POST the new car.
        String createdJson = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.base.id").exists())
                .andExpect(jsonPath("$.base.make").value("Toyota"))
                .andReturn().getResponse().getContentAsString();
//                .andExpect(status().is5xxServerError())
//                .andDo(MockMvcResultHandlers.print());
        // Parse the created car ID.
        JsonNode createdNode = objectMapper.readTree(createdJson);
        System.out.println("CREATED CAR NODE: "+createdNode.asText());
        Long createdId = createdNode.path("base").path("id").asLong();
        assertThat(createdId).isNotNull();
        System.out.println("CAR ID: " + createdId);
        // GET the newly created car.
        mockMvc.perform(get("/api/cars/{id}", createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.base.id").value(createdId))
                .andExpect(jsonPath("$.base.make").value("Toyota"));

        // Update the car.
        SedanCarRequest updateRequest = SedanCarRequest.builder()
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .price(BigDecimal.valueOf(24000)) // discounted price
                .description("A reliable sedan, now with discount.")
                .sellerId(1L)
                .trunkCapacity(16.0) // updated capacity
                .build();

        String updateJson = objectMapper.writeValueAsString(updateRequest);
        mockMvc.perform(put("/api/cars/{id}", createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.base.price").value(24000))
                .andExpect(jsonPath("$.base.description").value("A reliable sedan, now with discount."))
                .andExpect(jsonPath("$.trunkCapacity").value(16.0));

        // Delete the car.
        mockMvc.perform(delete("/api/cars/{id}", createdId))
                .andExpect(status().isNoContent());

        // Verify the car is deleted.
        mockMvc.perform(get("/api/cars/{id}", createdId))
                .andExpect(status().isNotFound());
    }
}
