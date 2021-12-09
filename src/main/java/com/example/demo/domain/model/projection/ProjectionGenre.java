package com.example.demo.domain.model.projection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;
import java.util.UUID;

public interface ProjectionGenre {

    UUID getGenreid();
    String getLabel();
    String getImage();

    @JsonIgnoreProperties("genres")
    Set<ProjectionAnime> getAnimes();
}
