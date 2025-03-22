package com.carsell.platform.service;

import com.carsell.platform.dto.CreateUserRequest;
import com.carsell.platform.dto.UpdateUserRequest;
import com.carsell.platform.dto.UserResponse;
import com.carsell.platform.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    UserResponse updateUserRoles(Long id, Set<User.Role> roles);

}
