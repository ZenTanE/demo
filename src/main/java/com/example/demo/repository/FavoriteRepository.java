package com.example.demo.repository;

import com.example.demo.domain.dto.UserDisplay;
import com.example.demo.domain.model.Favorite;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.projection.ProjectionFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Favorite.FavoriteId> {

    List<ProjectionFavorite> findByUserid(UUID uuid);
}
