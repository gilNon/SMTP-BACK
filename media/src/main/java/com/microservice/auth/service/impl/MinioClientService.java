package com.microservice.auth.service.impl;

import com.microservice.auth.exception.GeneralException;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio client service.
 * @author Gilberto Vazquez
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MinioClientService {

    private final MinioClient minioClient;

    private String bucketName = "smtp-media";

    /**
     * Method to upload a file to MinIO.
     */
    public void putObject(MultipartFile file,
                          String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage());
            throw new GeneralException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error uploading file to FileStorage");
        }
    }

    /**
     * Method to get a file from MinIO.
     * @param nameObject name of file.
     * @return file.
     */
    public String getFilePreSigned(String nameObject) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(nameObject)
                            .expiry(60 * 60 * 24)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error getting file from MinIO: {}", e.getMessage());
            throw new GeneralException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error getting file from FileStorage");
        }
    }

    public void createBucket() {
        try {
            boolean bucketExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!bucketExist) {
                log.info("CREATING BUCKET {}...", bucketName);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            log.error("Error to create bucket: {}", e.getMessage());
            throw new RuntimeException("Error to create bucket");
        }
    }

}
