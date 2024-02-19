package com.ebito.data_aggregator.api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Ответ сгенерированной справки
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrintedGuids {


    @Schema(description = "Ссылка на документ",
            example = "https://rtkit.minio.ru/documents/somedocument.pdf")
    private String link;

    @Schema(description = "Имя документа",
            example = "Выписка по начислениям абонента")
    private String name;

    @Schema(description = "Чек сумма документа",
            example = "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9")
    private String checkSum;

    @Schema(description = "Имя документа",
            example = "created_25_12_2020_08_47_50_jFEH2f.pdf")
    private String pdfFileName;

}
