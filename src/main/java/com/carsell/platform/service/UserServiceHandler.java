package com.carsell.platform.service;

import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.UpdateUserRequest;
import com.carsell.platform.dto.UserResponse;
import com.carsell.platform.entity.User;
import com.carsell.platform.exception.ResourceNotFoundException;
import com.carsell.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceHandler implements UserService {

    private static final String USER_NOT_FOUND_MSG = "User not found with id: %s";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // Utility method to map a User entity to a UserResponse DTO.
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .createdAt(user.getHistory() != null ? user.getHistory().getCreatedAt() : null)
                .updatedAt(user.getHistory() != null ? user.getHistory().getUpdatedAt() : null)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();
        return StreamSupport.stream(usersIterable.spliterator(), false)
                .map(this::mapToUserResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
        return mapToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        // In production, ensure the password is hashed using a PasswordEncoder.
        User user = User.builder()
                .username(request.getUsername())
                .login(request.getLogin())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))//request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();
        User savedUser = userRepository.save(user);
        log.info("Created new user with id: {}", savedUser.getId());
        return mapToUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setPhone(request.getPhone());

        User updatedUser = userRepository.save(existingUser);
        log.info("Updated user with id: {}", updatedUser.getId());
        return mapToUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
        userRepository.delete(existingUser);
        log.info("Deleted user with id: {}", id);
    }

    @Override
    @Transactional
    public UserResponse updateUserRoles(Long id, Set<User.Role> roles) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
        existingUser.getRoles().clear();
        existingUser.getRoles().addAll(roles);
        User updatedUser = userRepository.save(existingUser);
        log.info("Updated roles for user with id: {}", updatedUser.getId());
        return mapToUserResponse(updatedUser);
    }

}

