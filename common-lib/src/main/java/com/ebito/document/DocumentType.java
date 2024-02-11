package com.ebito.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentType {

    DOCX(".docx", "application/docx"),
    PDF(".pdf", "application/pdf");

    private final String documentExtension;
    private final String contentType;
}
