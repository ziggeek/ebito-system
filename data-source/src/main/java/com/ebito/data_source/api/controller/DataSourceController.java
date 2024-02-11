package com.ebito.data_source.api.controller;

import com.ebito.data_source.api.DataSourceApi;
import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.data_source.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataSourceController implements DataSourceApi {

    private final DataService dataService;

    @Override
    public ResponseEntity<DataResponse> getData(final String clientId) {
        final var response = dataService.getData(clientId);
        return ResponseEntity.ok(response);
    }
}
