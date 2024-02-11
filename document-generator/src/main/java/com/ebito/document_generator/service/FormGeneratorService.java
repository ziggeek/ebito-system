package com.ebito.document_generator.service;


import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.ebito.document.DocumentType;
import com.ebito.exceptionhandler.exception.ConvertToPdfException;
import com.ebito.exceptionhandler.exception.GeneratePrintedFormException;
import java.io.IOException;

/**
 * Интерефейс генерации печатной формы
 */
public interface FormGeneratorService {

    /**
     * Метод для генерации различных типов форм
     *
     * @param formData внешние данные, которые могут быть использованы в качестве основы для создания формы. Могут иметь различные типы
     * @return ответ с названием и ссылкой для получения формы из сервиса
     * @throws GeneratePrintedFormException при ошибке в шаблоне
     * @throws ConvertToPdfException при ошибке конвертации в PDF
     */
    FormGenerationResponse generate(String clientId, DocumentType docType, FormGenerationRequest formData) throws IOException;
}
