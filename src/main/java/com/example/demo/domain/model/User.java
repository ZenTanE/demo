package com.example.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="usser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID userid;

    public String username;
    public String password;
    public String role;
    public boolean enabled;

    @ManyToMany(mappedBy = "favoritedby")
    @JsonIgnoreProperties("favoritedby")
    public Set<Anime> favorites;

}