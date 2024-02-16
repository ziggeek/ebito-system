package com.ebito.document_generator.client.local.cloud.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Builder
@RequiredArgsConstructor
public class DocumentMultipartFile implements MultipartFile {

    private final byte[] bytes;
    private final String originalFilename;
    private final String contentType;

    @Override
    public String getName() {
        return originalFilename;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

//    @JsonIgnore
    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @JsonIgnore
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(java.io.File dest) throws IllegalStateException {
        throw new UnsupportedOperationException("transferTo() method is not supported");
    }
}

