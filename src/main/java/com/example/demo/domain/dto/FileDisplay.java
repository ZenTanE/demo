package com.example.demo.domain.dto;

import java.util.UUID;

public class FileDisplay {

    public UUID fileid;
    public String contenttype;

    public FileDisplay(UUID fileid, String contenttype) {
        this.fileid = fileid;
        this.contenttype = contenttype;
    }

}
