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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nachogarrone on 6/1/17.
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/add")
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            fileService.saveFile(uploadfile.getName(), uploadfile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity("File uploaded!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getFile(String id) {
        //String id = "5602de6e5d8bba0d6f2e45e4";
        GridFSDBFile file = null;
        try {
            file = fileService.getFile(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            return new ResponseEntity(file.getInputStream(), HttpStatus.OK);
        }
        return new ResponseEntity("File not found", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getFiles() {
        List<GridFSDBFile> files = null;
        try {
            files = fileService.getFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (files != null) {
            List<byte[]> streams = new ArrayList<>();
            for (GridFSDBFile gr : files) {
                try {
                    streams.add(IOUtils.toByteArray(gr.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity(streams, headers, HttpStatus.OK);
        }
        return new ResponseEntity("File not found", HttpStatus.OK);
    }
}
