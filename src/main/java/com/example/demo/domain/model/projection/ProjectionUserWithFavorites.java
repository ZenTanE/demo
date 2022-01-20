package com.example.demo.domain.model.projection;

import java.util.Set;
import java.util.UUID;

public interface ProjectionUserWithFavorites {

    Set<ProjectionAnime> getFavorites();

}