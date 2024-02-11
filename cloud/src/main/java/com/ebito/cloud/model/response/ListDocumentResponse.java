package com.ebito.cloud.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ListDocumentResponse {

    @Schema(description = "Список документов клиента")
    List<DocumentResponse> documents;
}
