package com.ebito.document_generator.template;

import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YargWrapper {

    private final FormGenerationRequest main;
}
