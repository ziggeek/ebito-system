package com.ebito.data_aggregator.api.controller;

import com.ebito.data_aggregator.rabbitmq.model.PrintFormGenerationRequest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.service.CommonService;
import com.ebito.data_aggregator.api.DataAggregatorApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataAggregatorController implements DataAggregatorApi {

    private final CommonService commonService;

    @Override
    public ResponseEntity<PrintedGuids> generatePrintForm(final String clientId, final PrintFormGenerationRequest request) {
        request.setClientId(clientId);
        final var response = commonService.selectPrintForm(request);
        return ResponseEntity.ok(response);
    }
}
