package com.carsell.platform.repository;

import com.carsell.platform.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByEmail(@Email(message = "Email should be valid") @NotBlank(message = "Email is required") String email);

}
