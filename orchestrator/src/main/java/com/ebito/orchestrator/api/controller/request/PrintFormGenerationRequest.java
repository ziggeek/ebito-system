package com.ebito.orchestrator.api.controller.request;

import com.ebito.channel.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Сообщение для генерации новой справки
 */
@Value
@Builder
public class PrintFormGenerationRequest {

    @Schema(description = "Код документа",
            example = "reference001")
    @NotNull
    String documentCode;

    @Schema(description = "Идентификатор клиента",
            example = "1")
    @NotNull
    String clientId;

    @Schema(description = "Канал запроса на генерацию документа",
            allowableValues = {"BRANCH", "ONLINE", "MOBILE"})
    Channel channel;

    @Schema(description = "Дата C",
            example = "1999-03-15")
    LocalDate dateFrom;

    @Schema(description = "Дата ПО",
            example = "2923-03-15")
    LocalDate dateTo;
}
