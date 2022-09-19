package com.fileupload.usecase2.controller;


import com.fileupload.usecase2.entity.ImageData;
import com.fileupload.usecase2.exception.ErrorMessages;
import com.fileupload.usecase2.model.ResponseData;
import com.fileupload.usecase2.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;


@RestController
@RequestMapping("/image")
public class DataController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "target") String target, @RequestParam("file") MultipartFile file) throws Exception {

        if(file.isEmpty()) throw new Exception(ErrorMessages.FILE_DOESNT_EXIST.getErrorMessage());

        if (Objects.equals(target, "DB")) {
            String uploadImage = service.uploadImage(file);
            ResponseData responseData = new ResponseData(new Date(), uploadImage);
            return new ResponseEntity<>(responseData, new HttpHeaders(),HttpStatus.OK);

        } else if (Objects.equals(target, "HDD")) {
            String uploadImage = service.uploadImageToFileSystem(file);
            ResponseData responseData = new ResponseData(new Date(), uploadImage);
            return new ResponseEntity<>(responseData, new HttpHeaders(),HttpStatus.OK);

        } else throw new Exception(ErrorMessages.WRONG_FORMAT.getErrorMessage());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long fileId) throws Exception {
        ImageData service1 = service.downloadImage(fileId);
        if(service1 == null) throw new Exception(ErrorMessages.FILE_ID_DOESNT_EXIST.getErrorMessage());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(service1.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + service1.getName()
                                + "\"")
                .body(new ByteArrayResource(service1.getImageData()));
    }
}
