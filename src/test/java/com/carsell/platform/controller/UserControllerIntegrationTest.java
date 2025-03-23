package com.carsell.platform.controller;

import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.UpdateUserRequest;
import com.carsell.platform.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testUserLifecycle() throws Exception {
        // 1. Create a new user (signup)
        CreateUserRequest createRequest = CreateUserRequest.builder()
                .username("user1")
                .login("user1")
                .email("user1@example.com")
                .name("John")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .roles(Set.of(User.Role.USER))
                .build();

        String createJson = objectMapper.writeValueAsString(createRequest);

        String createResponse = mockMvc.perform(post("/api/users/signup")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("user1"))
                .andReturn().getResponse().getContentAsString();
        // Parse created user ID
        JsonNode createNode = objectMapper.readTree(createResponse);
        Long userId = createNode.path("id").asLong();
        assertThat(userId).isNotNull();

        // Now perform a login to get a JWT token.
        // Adjust the login payload as required by your /login endpoint.
        String loginJson = "{\"username\":\"user1\", \"password\":\"password123\"}";
        // Assuming that the login endpoint returns the token in the response header "Authorization"
        // or in a JSON property "token". Here we try header first.
        var loginResult = mockMvc.perform(post("/api/auth/login")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        // Will store the JWT token after login
        var jwtToken = loginResult.getResponse().getHeader("Authorization");
        if (jwtToken == null || jwtToken.isBlank()) {
            // If not in header, try to read from JSON body.
            JsonNode jsonResponse = objectMapper.readTree(loginResult.getResponse().getContentAsString());
            jwtToken = jsonResponse.get("token").asText();
        }
        // Ensure token has the "Bearer " prefix if your filters expect it:
        if (!jwtToken.startsWith("Bearer ")) {
            jwtToken = "Bearer " + jwtToken;
        }
        assertThat(jwtToken).isNotBlank();


        // 2. Retrieve the user by ID
        mockMvc.perform(get("/api/users/{id}", userId)
                        .header("Authorization", jwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));

        // 3. Retrieve all users and verify that our new user exists
        mockMvc.perform(get("/api/users")
                        .header("Authorization", jwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id==" + userId + ")]").exists());

        // 4. Update the user (e.g., change first name, email, and roles)
        UpdateUserRequest updateRequest = UpdateUserRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .username("Doe")
                .email("user1_updated@example.com")
                .password("newPassword123") // if password update is allowed
                .roles(Set.of(User.Role.USER, User.Role.ADMIN))
                .build();
        String updateJson = objectMapper.writeValueAsString(updateRequest);

        String updateResponse = mockMvc.perform(put("/api/users/{id}", userId)
                        .with(csrf().asHeader())
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("user1_updated@example.com"))
                .andReturn().getResponse().getContentAsString();

        // Optionally, parse updateResponse and further assert fields.

        // 5. Delete the user
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .with(csrf().asHeader())
                        .header("Authorization", jwtToken)
                )
                .andExpect(status().isNoContent());

        // 6. Verify the user is deleted (expect 404)
        mockMvc.perform(
                        get("/api/users/{id}", userId)
                                .header("Authorization", jwtToken)
                )
                .andExpect(status().isNotFound());
    }

}
