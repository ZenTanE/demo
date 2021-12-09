package com.example.demo.controller;

import com.example.demo.domain.dto.Message;
import com.example.demo.domain.dto.ResponseList;
import com.example.demo.domain.model.Anime;
import com.example.demo.domain.model.projection.ProjectionAnime;
import com.example.demo.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    @Autowired
    private AnimeRepository animeRepository;


    // returns a list of all anime objects found in the repository
    @GetMapping("/")
    public ResponseEntity<?> findAllAnimes() {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseList(animeRepository.findBy(ProjectionAnime.class)));

    }

    // returns an anime object if the id is found in the repository,
    // otherwise returns an error message
    @GetMapping("/{id}")
    public ResponseEntity<?> findAnime(@PathVariable UUID id) {

        if (animeRepository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.OK).body(
                    animeRepository.findById(id)
            );

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat l'anime amd id \'"
                            + id.toString() + "\'");

        }

    }

    // adds an Anime to the repository, returns an ok message
    // with the Anime, but if the Anime wasn't found in the repository,
    // it returns an error message
    @PostMapping("/")
    public ResponseEntity<?> createAnime(@RequestBody Anime anime) {


        if (animeRepository.findByName(anime.name) == null) {

            return ResponseEntity.status(HttpStatus.OK).body(
                    animeRepository.save(anime));

        } else {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new Message("Ja existeix un anime amb el nom \'"
                            + anime.name + "\'"));

        }

    }

    // deletes an anime from the repository and returns an ok message if found,
    // otherwise returns an error message
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnime(@PathVariable UUID id) {

        if (animeRepository.existsById(id)) {

            animeRepository.delete(
                    animeRepository.findById(id)
                            .orElse(null));

            return ResponseEntity.status(HttpStatus.OK).body(
                    "S'ha eliminat l'anime amb id \'"
                            + id.toString() + "\'");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat l'anime amb id \'"
                            + id.toString() + "\'");

        }

    }

}
