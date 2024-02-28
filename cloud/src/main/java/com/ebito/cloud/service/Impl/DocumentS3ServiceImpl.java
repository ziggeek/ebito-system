package com.ebito.cloud.service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.CloudProperties;
import com.ebito.cloud.service.DocumentService;
import com.ebito.exceptionhandler.exception.FileProcessingException;
import com.ebito.exceptionhandler.exception.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class DocumentS3ServiceImpl implements DocumentService {

    private final CloudProperties cloudProperties;
    private final AmazonS3 amazonS3;

    @Override
    public DocumentEntity upload(MultipartFile file, String clientId) {
        try (InputStream inputStream = file.getInputStream()) {
            String fileName = file.getOriginalFilename();
            saveDocument(inputStream, fileName);
            return new DocumentEntity(clientId, fileName);
        } catch (Exception e) {
            throw new FileProcessingException("Document upload failed: " + e.getMessage());
        }
    }

    @Override
    public String downloadUrl(String name) {
        try {
            String url = loadUrlDocument(name);
            Assert.notNull(url, "Document url must not be null");
            log.debug("Document file {} downloaded successfully", name);
            return url;
        } catch (Exception e) {
            throw new InvalidUrlException(e.getMessage());
        }
    }

    @SneakyThrows
    private void saveDocument(InputStream inputStream, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        amazonS3.putObject(cloudProperties.getBucket(), fileName, inputStream, metadata);
        log.debug("Document file {} uploaded successfully", fileName);
    }

    @SneakyThrows
    private String loadUrlDocument(String name) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(cloudProperties.getBucket(), name)
                .withMethod(HttpMethod.GET)
                .withExpiration(DateTime.now().plusMinutes(5).toDate());
        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString();
    }

}



