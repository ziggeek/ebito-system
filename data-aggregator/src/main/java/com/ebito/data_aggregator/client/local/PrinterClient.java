package com.ebito.data_aggregator.client.local;

import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.document.DocumentType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "printed-form",
        url = "${app.feign.client.config.print.url}",
        path = "/api/v1"
)
public interface PrinterClient {

    /**
     * Отправить запрос на генерацию справки в document-generator
     * @param request данные для генерации
     * @return ответ сгенерированной справки
     */
    @PostMapping(value = "/forms/generate-print-form")
    ResponseEntity<PrintedGuids> generatePrintForm(
            @RequestParam(name = "docType", required = false) DocumentType docType,
            @RequestParam(name = "clientId") long clientId,
            @RequestBody PrintData request);
}
