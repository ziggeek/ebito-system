package com.ebito.document_generator.api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FormGenerationResponse {

    @Schema(description = "Ссылка на документ",
            example = "https://rtkit.minio.ru/documents/somedocument.pdf")
    private final String link;

    @Schema(description = "Имя документа",
            example = "Выписка по начислениям абонента")
    private final String name;

    @Schema(description = "Чек сумма документа",
            example = "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9")
    private final String checkSum;

    @Schema(description = "Имя документа",
            example = "created_25_12_2020_08_47_50_jFEH2f.pdf")
    private final String pdfFileName;
}
