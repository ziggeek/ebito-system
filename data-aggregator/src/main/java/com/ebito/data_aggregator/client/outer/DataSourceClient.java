package com.ebito.data_aggregator.client.outer;

import com.ebito.data_aggregator.client.outer.response.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "data-source",
        url = "${app.feign.client.config.data-source.url}",
        path = "/api/v1"
)
public interface DataSourceClient {

    /**
     * Отправить запрос на получение данных в data-source
     * @param clientId id клиента, данные которого нужно получить
     * @return данные клиента
     */
    @GetMapping(value = "/clients/{clientId}/get-data")
    ResponseEntity<DataResponse> getData(@PathVariable String clientId);
}
