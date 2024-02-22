package com.ebito.cloud.service.Impl;

import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.MinioProperties;
import com.ebito.cloud.service.DocumentService;
import com.ebito.exceptionhandler.exception.BucketProcessingException;
import com.ebito.exceptionhandler.exception.FileProcessingException;
import com.ebito.exceptionhandler.exception.InvalidUrlException;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public DocumentEntity upload(final MultipartFile file, final String clientId) {
        try {
            //Создание корзины для хранения файлов.
            createBucket();
        } catch (Exception e) {
            throw new BucketProcessingException("Bucket failed: "
                    + e.getMessage());
        }
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
     * Метод проверки создан ли корзина MiniO для хранения файлов.
     * Если нет, то создание корзины для хранения файлов.
     */
    @SneakyThrows
    private void createBucket() {
        log.debug("Bucket {} created successfully", minioProperties.getBucket());
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    /**
     * Метод загрузки файла в корзину MiniO.
     * @param inputStream файл для загрузки.
     * @param fileName имя файла.
     */

    @SneakyThrows
    private void saveDocument(final InputStream inputStream,
                              final String fileName) {
        log.debug("Document file {} uploaded successfully", fileName);
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

    /**
     * Загрузка файла с корзины MiniO.
     * @param name имя файла.
     * @return ссылка на файл.
     */

    @SneakyThrows
    private String loadUrlDocument(final String name) {
        log.debug("Document URL {} downloaded successfully", name);
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioProperties.getBucket())
                        .object(name)
                        .expiry(5, TimeUnit.MINUTES)
                        .build());
    }

}
