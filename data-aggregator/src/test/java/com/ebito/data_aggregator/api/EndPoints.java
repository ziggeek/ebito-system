package com.ebito.data_aggregator.api;

public class EndPoints {

    private EndPoints() {}

    public static final String CONTEXT_PATH = "/api/v1";

    public static final class DataAggregatorController {
        public static final String PATH = "/clients/%d";

        public static final String GENERATE_PRINT_FORM = PATH + "/generate-print-form";
    }
}
