package com.ebito.orchestrator.api.controller;

import com.ebito.orchestrator.api.OrchestratorApi;
import com.ebito.orchestrator.api.controller.response.ClientDocuments;
import com.ebito.orchestrator.client.cloud.CloudClient;
import com.ebito.orchestrator.client.data_aggregator.DataAggregatorClient;
import com.ebito.orchestrator.rabbitmq.RabbitMqSender;
import com.ebito.orchestrator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.orchestrator.api.controller.response.PrintedGuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrchestratorController implements OrchestratorApi {

    private final DataAggregatorClient dataAggregatorClient;
    private final CloudClient cloudClient;
    private final RabbitMqSender rabbitMqSender;

    @Value("${app.connections.isAsyncConnectEnabled:false}")
    private Boolean isAsyncConnectEnabled;

    @Override
    public ResponseEntity<ClientDocuments> getClientDocuments(final String clientId) {
        final ClientDocuments response = cloudClient.getClientDocuments(clientId).getBody();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> generateDocument(final String clientId, final PrintFormGenerationRequest request) {
        if (isAsyncConnectEnabled) {
            rabbitMqSender.send(request);
        } else {
            dataAggregatorClient.generateReference(clientId, request).getBody();
        }
        return ResponseEntity.ok().build();
    }
}
