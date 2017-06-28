package com.sd.chat.controllers;

import com.mongodb.gridfs.GridFSDBFile;
import com.sd.chat.services.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by nachogarrone on 6/1/17.
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<?> saveFile(String username, @RequestParam("file") MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            String id = fileService.saveFile(username, uploadfile);
            return new ResponseEntity(id, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity("Failed to save file.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getFile(String id) {
        GridFSDBFile file = null;
        try {
            file = fileService.getFile(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            try {

                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity(IOUtils.toByteArray(file.getInputStream()), headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity("File not found", HttpStatus.OK);
    }
}
