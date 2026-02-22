package com.fakesmtp.api.service.impl;

import com.fakesmtp.api.exception.GeneralException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Minio client service.
 * @author Gilberto Vazquez
 */
@Component
@AllArgsConstructor
@Slf4j
public class MinioClientService {
    private final MinioClient minioClient;

    /**
     * Method to upload a file to MinIO.
     * @param contentType content type of object.
     * @param inputStream stream.
     * @param size size.
     * @param bucketName name of bucket.
     * @param fileName name of file.
     */
    public void putObject(String contentType, InputStream inputStream, long size,
                          String bucketName, String fileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .contentType(contentType)
                            .stream(inputStream, size, -1)
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage());
        }
    }

    /**
     * Method to get a file from MinIO.
     * @param bucketName name of bucket.
     * @param nameObject name of file.
     * @return file.
     */
    public String getFilePreSigned(String bucketName, String nameObject) {
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

}
