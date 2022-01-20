package com.example.demo.controller;

import com.example.demo.domain.dto.ResponseList;
import com.example.demo.domain.model.Genre;
import com.example.demo.domain.model.projection.ProjectionAuthor;
import com.example.demo.domain.model.projection.ProjectionGenre;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;


    // returns a list of all author objects found in the repository
    @GetMapping("/")
    public ResponseEntity<?> findAllGenres() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseList(genreRepository.findBy(ProjectionGenre.class)));

    }

    // returns a Genre object if the id is found in the repository,
    // otherwise returns an error message
    @GetMapping("/{id}")
    public ResponseEntity<?> findGenre(@PathVariable UUID id) {

        if (genreRepository.existsById(id)) {

            // TODO *testing* {



            // TODO *testing* }

            return ResponseEntity.status(HttpStatus.OK).body(
                    genreRepository.findById(id)
            );

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat el gènere amd id \'"
                            + id.toString() + "\'");

        }

    }

}
