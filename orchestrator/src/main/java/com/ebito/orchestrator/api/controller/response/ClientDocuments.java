package com.ebito.orchestrator.api.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Список документов клиента
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDocuments {

    @Schema(description = "Список документов клиента")
    List<ClientDocument> documents;
}
