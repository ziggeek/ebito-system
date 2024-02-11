package com.ebito.data_aggregator.service.reference.reference001;

import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.service.PrinterService;
import com.ebito.data_aggregator.service.Reference000Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Reference001Service extends Reference000Service {

    private final PrinterService printerService;

    @Override
    public PrintedGuids execute(final PrintData printData) {
        log.info("Reference001Service executed request = {}", printData);
        return printerService.getPrint(printData);
    }
}
