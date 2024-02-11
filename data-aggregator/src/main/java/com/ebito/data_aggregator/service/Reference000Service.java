package com.ebito.data_aggregator.service;

import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;

public abstract class Reference000Service {
    public abstract PrintedGuids execute(PrintData printData);
}

