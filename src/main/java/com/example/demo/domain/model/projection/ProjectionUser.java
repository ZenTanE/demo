package com.example.demo.domain.model.projection;

import javax.persistence.*;
import java.util.UUID;

public interface ProjectionUser {
    UUID getUserid();

    String getUsername();

}