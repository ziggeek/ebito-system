package com.ebito.data_aggregator.rabbitmq.model;


import com.ebito.channel.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Запрос для генерации новой справки
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PrintFormGenerationRequest {

    @Schema(description = "Код документа",
            example = "reference001")
    @NotNull(message = "Document code must not be null")
    private String documentCode;

    @Schema(description = "Идентификатор клиента",
            example = "1")
    @NotNull(message = "Client id must not be null")
    private String clientId;

    @Schema(description = "Канал запроса на генерацию документа")
    @NotNull(message = "Channel must not be null")
    private Channel channel;

    @Schema(description = "Дата C",
            example = "2000-03-15")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date from must not be null")
    private LocalDate dateFrom;

    @Schema(description = "Дата ДО",
            example = "2024-03-15")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date to must not be null")
    private LocalDate dateTo;
}
