package com.ebito.orchestrator.api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Ответ сгенерированной справки
 */
@Value
@Builder
public class PrintedGuids {


    @Schema(description = "Ссылка на документ",
            example = "https://rtkit.minio.ru/documents/somedocument.pdf")
    String link;

    @Schema(description = "Имя документа",
            example = "Выписка по начислениям абонента")
    String name;

    @Schema(description = "Чек сумма документа",
            example = "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9")
    String checkSum;

    @Schema(description = "Имя документа",
            example = "created_25_12_2020_08_47_50_jFEH2f.pdf")
    String pdfFileName;

}
