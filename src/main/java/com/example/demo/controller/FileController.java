package com.example.demo.controller;

import com.example.demo.domain.dto.FileDisplay;
import com.example.demo.domain.model.File;
import com.example.demo.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile uploadedFile) {
        try {
            File file = new File();
            file.contenttype = uploadedFile.getContentType();
            file.data = uploadedFile.getBytes();

            fileRepository.save(file);

            FileDisplay fileDisplay = new FileDisplay(file.fileid, file.contenttype);
            return ResponseEntity.ok().body(fileDisplay);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllFiles() {

        return ResponseEntity.ok().body(fileRepository.findBy());

    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
        File file = fileRepository.findById(id).orElse(null);

        if (file == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(file.contenttype))
                .contentLength(file.data.length)
                .body(file.data);
    }

    @DeleteMapping("/")
    public void thanosSnap() {

        fileRepository.deleteAll();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable UUID id) {

        if (fileRepository.existsById(id)) {

            fileRepository.delete(
                    fileRepository.findById(id)
                            .orElse(null)
            );

            return ResponseEntity.status(HttpStatus.OK).body(
                    "S'ha eliminat l'arxiu amb id \'"
                            + id.toString() + "\'");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No s'ha trobat l'arxiu amb id \'"
                            + id.toString() + "\'");

        }

    }

    /*
    @GetMapping
    public String hack() {
        List<String> files = fileRepository.getFileIds();

        String filesStr = "";
        for (String file : files) {
            filesStr += "<img src='/files/"+file+"' style='width:15em'>";
        }

        return "<form method=\"POST\" enctype=\"multipart/form-data\" style=\"display:flex;\">\n" +
                "    <input id=\"file\" type=\"file\" name=\"file\" style=\"display:none\" onchange=\"document.getElementById('preview').src=window.URL.createObjectURL(event.target.files[0])\">\n" +
                "    <label for=\"file\" style=\"border: 1px dashed #999\">\n" +
                "        <img id=\"preview\" src=\"\" style=\"width:64px;\">\n" +
                "    </label>\n" +
                "    <input type=\"submit\" style=\"background:#0096f7;color: white;border: 0;border-radius: 3px;padding: 8px;\" value=\"Upload\">\n" +
                "</form>\n <div style='display:flex;flex-wrap:wrap;gap:1em;'>" + filesStr + "</div>";
    }
    */

}