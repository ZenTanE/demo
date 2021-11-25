package com.example.demo.repository;

import com.example.demo.domain.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {

    Anime findByName(String name);
}
