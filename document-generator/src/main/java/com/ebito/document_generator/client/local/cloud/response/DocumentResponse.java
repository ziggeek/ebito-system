package com.ebito.document_generator.client.local.cloud.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DocumentResponse {

    @Schema(description = "Имя документа",
            example = "Выписка по начислениям абонента")
    String name;

    @Schema(description = "Ссылка на документ",
            example = "https://example.com/12345.pdf")
    String link;

}
