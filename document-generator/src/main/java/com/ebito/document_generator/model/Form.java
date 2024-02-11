package com.ebito.document_generator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Form {
    REFERENCE_001_BRANCH("references/reference001/reference-001-branch-template.docx",
            "Выписка по начислениям абонента", "reference001"),
    REFERENCE_001_MOBILE("references/reference001/reference-001-mobile-template.docx",
            "Выписка по начислениям абонента", "reference001"),
    REFERENCE_001_ONLINE("references/reference001/reference-001-online-template.docx",
            "Выписка по начислениям абонента", "reference001")
    ;

    private final String path;
    private final String documentName;
    private final String code;
}
