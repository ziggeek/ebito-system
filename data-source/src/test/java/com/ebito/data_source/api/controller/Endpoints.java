package com.ebito.data_source.api.controller;

public class Endpoints {
        public static final String CONTEXT_PATH = "/api/v1";

        public static final class PrinterController {

            public static final String PATH = "/clients";

            public static final String GET_DATA = PATH + "/{clientId}/get-data";

        }
}
