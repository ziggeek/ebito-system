package com.ebito.cloud.service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.MinioProperties;
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

    private final MinioProperties minioProperties;

    private final AmazonS3 amazonS3;


    @Override
    public DocumentEntity upload(final MultipartFile file, final String clientId) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileProcessingException("Document upload failed: "
                    + e.getMessage());
        }
        saveDocument(inputStream, file.getOriginalFilename());
        log.debug("Document file {} uploaded successfully", file.getOriginalFilename());
        return new DocumentEntity(clientId, file.getOriginalFilename());
    }

    @Override
    public String downloadUrl(final String name) {
        try {
            String url = loadUrlDocument(name);
            Assert.notNull(url, "Document url must not be null");
            log.debug("Document file {} downloaded successfully", name);
            return url;
        } catch (Exception e) {
            throw new InvalidUrlException(e.getMessage());
        }

    }

    /**
     * Метод загрузки файла в корзину MiniO.
     * @param inputStream файл для загрузки.
     * @param fileName имя файла.
     */
    @SneakyThrows
    private void saveDocument(final InputStream inputStream, final String fileName) {
        log.debug("Document file {} uploaded successfully", fileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        amazonS3.putObject(new PutObjectRequest(minioProperties.getBucket(), fileName, inputStream, metadata));
    }

    /**
     * Загрузка файла с корзины MiniO.
     * @param name имя файла.
     * @return ссылка на файл.
     */
    @SneakyThrows
    private String loadUrlDocument(final String name) {
        log.debug("Document URL {} downloaded successfully", name);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(minioProperties.getBucket(), name)
                .withMethod(HttpMethod.GET)
                .withExpiration(DateTime.now().plusMinutes(5).toDate());
        URL url = amazonS3.generatePresignedUrl(request);
        return url.toString();
    }

}
