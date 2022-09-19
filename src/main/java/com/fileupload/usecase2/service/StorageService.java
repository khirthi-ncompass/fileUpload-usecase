package com.fileupload.usecase2.service;

import com.fileupload.usecase2.entity.ImageData;
import com.fileupload.usecase2.exception.ErrorMessages;
import com.fileupload.usecase2.model.ResponseData;
import com.fileupload.usecase2.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    private final String FOLDER_PATH = "/C://Users//User//Desktop//UseCase2/";

        public ResponseEntity<?> upload(Target target, MultipartFile file) throws Exception {
            if (target == Target.DB) {
                String uploadImage = uploadImage(file);
                ResponseData responseData = new ResponseData(new Date(), uploadImage);
                return new ResponseEntity<>(responseData, new HttpHeaders(), HttpStatus.OK);

        } else if (target == Target.HDD) {
            String uploadImage = uploadImageToFileSystem(file);
            ResponseData responseData = new ResponseData(new Date(), uploadImage);
            return new ResponseEntity<>(responseData, new HttpHeaders(), HttpStatus.OK);

        } else throw new Exception(ErrorMessages.WRONG_FORMAT.getErrorMessage());
    }



        public String uploadImage (MultipartFile file) throws Exception {
            ImageData imageData = repository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(file.getBytes()).build());

            if (imageData == null) {
                throw new Exception(ErrorMessages.FILE_DOESNT_EXIST.getErrorMessage());
            } else {
                return "file uploaded successfully: " + file.getOriginalFilename();
            }
        }

        public ImageData downloadImage ( long fileId) throws Exception {
            return repository
                    .findById(fileId)
                    .orElseThrow(
                            () -> new Exception("File not found with Id: " + fileId));
        }

        /*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

        public String uploadImageToFileSystem (MultipartFile file) throws IOException {
            String filePath = FOLDER_PATH + file.getOriginalFilename();

            //transfers the file to desired folder
            file.transferTo(new File(filePath));

            return "file uploaded successfully : " + filePath;
        }

}

