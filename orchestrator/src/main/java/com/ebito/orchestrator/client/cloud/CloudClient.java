package com.ebito.orchestrator.client.cloud;

import com.ebito.orchestrator.api.controller.response.ClientDocuments;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "cloud",
        url = "${app.feign.client.config.cloud.url}",
        path = "/api/v1/")
public interface CloudClient {

    @GetMapping("/{clientId}/get-client-documents")
    ResponseEntity<ClientDocuments> getClientDocuments(@PathVariable("clientId") String clientId);
}
