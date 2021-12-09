package com.example.demo.controller;

import com.example.demo.domain.dto.Message;
import com.example.demo.domain.dto.ResponseList;
import com.example.demo.domain.model.Anime;
import com.example.demo.domain.model.Author;
import com.example.demo.domain.model.projection.ProjectionAnime;
import com.example.demo.domain.model.projection.ProjectionAuthor;
import com.example.demo.repository.AnimeRepository;
import com.example.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;


    // returns a list of all author objects found in the repository
    @GetMapping("/")
    public ResponseEntity<?> findAllAuthors() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseList(authorRepository.findBy(ProjectionAuthor.class)));

    }

    // returns an author object if the id is found in the repository,
    // otherwise returns an error message
    @GetMapping("/{id}")
    public ResponseEntity<?> findAuthor(@PathVariable UUID id) {

        if (authorRepository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.OK).body(
                    authorRepository.findById(id)
            );

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat l'autor amd id \'"
                            + id.toString() + "\'");

        }

    }

}
