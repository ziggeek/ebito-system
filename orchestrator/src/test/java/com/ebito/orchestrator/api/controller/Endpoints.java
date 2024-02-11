package com.ebito.orchestrator.api.controller;

public class Endpoints {
        public static final String CONTEXT_PATH = "/api/v1";

        public static final class PrinterController {
            public static final String PATH = "/clients";
            public static final String GET_DOCUMENTS = "/{clientId}/get-client-documents";
            public static final String GENERATE_DOCUMENT = PATH + "/{clientId}/generate-document";

        }
}
