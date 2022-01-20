package com.example.demo.controller;

import com.example.demo.domain.dto.FavoriteCreateRequest;
import com.example.demo.domain.dto.Message;
import com.example.demo.domain.dto.ResponseList;
import com.example.demo.domain.dto.UserRegisterRequest;
import com.example.demo.domain.model.Favorite;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.projection.ProjectionAnime;
import com.example.demo.domain.model.projection.ProjectionUser;
import com.example.demo.domain.model.projection.ProjectionUserWithFavorites;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userRepository.findByUserid(id, ProjectionUser.class));
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

    // returns the IDs of the starred animes
    @GetMapping("/favs")
    public ResponseEntity<?> getFavs(Authentication authentication){

        return ResponseEntity.ok().body(
               new ResponseList(favoriteRepository.findByUserid(userRepository.findByUsername(authentication.getName()).userid)));
    }

    @GetMapping("/favs/details")
    public ResponseEntity<?> getFavsDetails(Authentication authentication){

        return ResponseEntity.ok().body(
        new ResponseList(userRepository.findByUserid(userRepository.findByUsername(authentication.getName()).userid, ProjectionUserWithFavorites.class)));
    }

    @PostMapping("/favs")
    public ResponseEntity<?> addFav(@RequestBody FavoriteCreateRequest favoriteCreateRequest, Authentication authentication){

        Favorite favorite = new Favorite();
        favorite.animeid = favoriteCreateRequest.animeid;
        favorite.userid = userRepository.findByUsername(authentication.getName()).userid;

        favoriteRepository.save(favorite);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/favs/{id}")
    public ResponseEntity<?> deleteFav(@PathVariable UUID id, Authentication authentication) {

        Favorite favorite = new Favorite();
        favorite.userid = userRepository.findByUsername(authentication.getName()).userid;
        favorite.animeid = id;

        favoriteRepository.delete(favorite);

        return ResponseEntity.ok().build();

    }

}