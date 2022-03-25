package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PhotoService {

    String uploadImage(MultipartFile image) throws IOException;

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    String generateFileName(MultipartFile multiPart);

    void initializeAmazon();


}
