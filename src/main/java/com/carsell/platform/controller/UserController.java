package com.carsell.platform.controller;

import com.carsell.platform.UsersRepository;
import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.UpdateUserRequest;
import com.carsell.platform.dto.UserResponse;
import com.carsell.platform.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = StreamSupport.stream(
                usersRepository.findAll().spliterator(), false)
                .map(this::mapToUserResponse).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return usersRepository.findById(id)
                .map(user -> ResponseEntity.ok(mapToUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .phone(createUserRequest.getPhone())
                .build();
        // The History embeddable will be auto-populated by Hibernate during persist.
        User savedUser = usersRepository.save(user);
        return new ResponseEntity<>(mapToUserResponse(savedUser), HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return usersRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(updateUserRequest.getUsername());
                    existingUser.setEmail(updateUserRequest.getEmail());
                    existingUser.setPassword(updateUserRequest.getPassword());
                    existingUser.setFirstName(updateUserRequest.getFirstName());
                    existingUser.setLastName(updateUserRequest.getLastName());
                    existingUser.setPhone(updateUserRequest.getPhone());
                    User updatedUser = usersRepository.save(existingUser);
                    return new ResponseEntity<>(mapToUserResponse(updatedUser), HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return usersRepository.findById(id)
                .map(user -> {
                    usersRepository.delete(user);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getHistory().getCreatedAt())
                .updatedAt(user.getHistory().getUpdatedAt())
                .build();
    }
    
}
