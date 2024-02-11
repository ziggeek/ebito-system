package com.ebito.document_generator.api;

public class EndPoints {

    private EndPoints() {}

    public static final String CONTEXT_PATH = "/api/v1";

    public static final class PrinterController {

        public static final String PATH = "/forms";

        public static final String GENERATE_PRINT_FORM = PATH + "/generate-print-form";

    }
}
