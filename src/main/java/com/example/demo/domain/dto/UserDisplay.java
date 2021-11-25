package com.example.demo.domain.dto;

import java.util.UUID;

public class UserDisplay {

    public UUID userid;
    public String username;

    public UserDisplay(UUID userid, String username) {
        this.userid = userid;
        this.username = username;
    }

}
