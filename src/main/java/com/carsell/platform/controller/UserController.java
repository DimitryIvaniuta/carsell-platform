package com.carsell.platform.controller;

import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.UpdateUserRequest;
import com.carsell.platform.dto.UserResponse;
import com.carsell.platform.entity.User;
import com.carsell.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a list of UserResponse DTOs
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    /**
     * Retrieves a user by ID.
     *
     * @param id the user identifier
     * @return the UserResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable final Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Creates a new user (signup).
     *
     * @param createUserRequest the user creation request payload
     * @return the created user as a UserResponse DTO
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     *
     * @param id                the user identifier
     * @param updateUserRequest the update payload
     * @return the updated UserResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable final Long id,
                                                   @Valid @RequestBody final UpdateUserRequest updateUserRequest) {
        UserResponse userResponse = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the user identifier
     * @return HTTP 204 No Content if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates user roles by ID.
     *
     * @param id    the user identifier.
     * @param roles the user roles.
     * @return the updated UserResponse DTO.
     */
    @PatchMapping("/{id}/roles")
    public ResponseEntity<UserResponse> updateUserRoles(@PathVariable final Long id,
                                                        @RequestBody final Set<User.Role> roles) {
        UserResponse userResponse = userService.updateUserRoles(id, roles);
        return ResponseEntity.ok(userResponse);
    }

}
