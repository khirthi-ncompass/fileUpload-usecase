package com.fileupload.usecase2.repository;

import com.fileupload.usecase2.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData, Long> {

}
