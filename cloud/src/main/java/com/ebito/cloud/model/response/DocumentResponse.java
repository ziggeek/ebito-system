package com.ebito.cloud.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Ответ сгенерированной справки
 */
@Value
@Builder
public class DocumentResponse {

    @Schema(description = "Имя документа",
            example = "created_25_12_2020_08_47_50_jFEH2f.pdf")
    String name;

    @Schema(description = "Ссылка на документ",
            example = "https://example.com/12345.pdf")
    String link;

}
