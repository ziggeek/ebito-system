package com.ebito.orchestrator.api.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Ссылка на готовую справку клиента
 */

@Value
@Builder
public class ClientDocument {

    @Schema(description = "Имя документа",
            example = "created_25_12_2020_08_47_50_jFEH2f.pdf")
    String name;

    @Schema(description = "Ссылка на документ",
            example = "https://rtkit.minio.ru/documents/somedocument.pdf")
    String link;
}
