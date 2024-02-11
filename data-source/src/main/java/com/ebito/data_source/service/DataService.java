package com.ebito.data_source.service;

import com.ebito.data_source.api.controller.response.DataResponse;

public interface DataService {

    DataResponse getData(String clientId);
}
