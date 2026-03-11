package com.microservice.smtp.mapper;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.activation.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class MultipartMapper {
    public static List<MultipartFile> convert(List<DataSource> dataSources) {

        return dataSources.stream()
                .map(ds -> {
                    try (InputStream is = ds.getInputStream()) {

                        return new MockMultipartFile(
                                ds.getName(),
                                ds.getName(),
                                ds.getContentType(),
                                is
                        );

                    } catch (Exception e) {
                        throw new RuntimeException("Error converting DataSource to MultipartFile", e);
                    }
                })
                .collect(Collectors.toList());
    }
}
