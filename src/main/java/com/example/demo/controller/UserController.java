package com.example.demo.controller;

import com.example.demo.domain.dto.Message;
import com.example.demo.domain.dto.UserRegisterRequest;
import com.example.demo.domain.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok().body(userRepository.findBy());

    }

    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (userRepository.findByUsername(userRegisterRequest.username) == null) {
            User user = new User();
            user.username = userRegisterRequest.username;
            user.password = passwordEncoder.encode(userRegisterRequest.password);
            user.enabled = true;
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new Message("Ja existeix un usuari amb el nom \'"
                        + userRegisterRequest.username + "\'"));
    }

    @DeleteMapping("/")
    public void thanosSnap() {

        userRepository.deleteAll();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {

        if (userRepository.existsById(id)) {

            userRepository.delete(
                    userRepository.findById(id)
                            .orElse(null)
            );

            return ResponseEntity.status(HttpStatus.OK).body(
                    "S'ha eliminat l'usuari amb id \'"
                            + id.toString() + "\'");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat l'usuari amb id \'"
                            + id.toString() + "\'");

        }

    }

}
