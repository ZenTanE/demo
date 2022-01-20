package com.example.demo.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="favorite")
@IdClass(Favorite.FavoriteId.class)
public class Favorite {
    public static class FavoriteId implements Serializable {
        public UUID userid;
        public UUID animeid;

        public FavoriteId() {
        }

        public FavoriteId(UUID userid, UUID animeid) {
            this.userid = userid;
            this.animeid = animeid;
        }
    }

    @Id
    public UUID userid;

    @Id
    public UUID animeid;

}