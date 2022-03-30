package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.PhotoService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import com.alkemy.ong.config.AmazonS3Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private AmazonS3 s3client;

    @Autowired
    private AmazonS3Configuration amazonS3Configuration;

    @Override
    public String uploadImage(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = amazonS3Configuration.getEndpointUrl() + "/" + amazonS3Configuration.getBucketName() + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    @Override
    @PostConstruct
    public void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(amazonS3Configuration.getAccessKey(), amazonS3Configuration.getSecretKey());
        this.s3client = new AmazonS3Client(credentials);
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(amazonS3Configuration.getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

}
