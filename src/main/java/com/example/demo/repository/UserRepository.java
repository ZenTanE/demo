package com.example.demo.repository;

import com.example.demo.domain.dto.UserDisplay;
import com.example.demo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    List<UserDisplay> findBy();

}
