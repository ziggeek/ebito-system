package com.ebito.orchestrator.client.data_aggregator;

import com.ebito.orchestrator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.orchestrator.api.controller.response.PrintedGuids;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "data-aggregator",
        url = "${app.feign.client.config.data-aggregator.url}",
        path = "/api/v1")
public interface DataAggregatorClient {

    @PostMapping("/clients/{clientId}/generate-print-form")
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") String clientId,
                                                   @RequestBody PrintFormGenerationRequest request);
}
