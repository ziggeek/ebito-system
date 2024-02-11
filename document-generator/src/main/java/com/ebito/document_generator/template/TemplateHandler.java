package com.ebito.document_generator.template;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores templates and
 * finds template by its name
 */
@Component
@Slf4j
public class TemplateHandler {

    /**
     * stores templates
     */
    private final Map<String, byte[]> storage = new HashMap<>();

    @SneakyThrows
    @PostConstruct
    private void loadTemplates() {
        List<String> templateNames = List.of(
                "templates/references/reference001/reference-001-branch-template.docx",
                "templates/references/reference001/reference-001-mobile-template.docx",
                "templates/references/reference001/reference-001-online-template.docx"
        );

        for (String templateName : templateNames) {
            ClassPathResource templateResource = new ClassPathResource(templateName);
            try (InputStream inputStream = templateResource.getInputStream()) {
                byte[] bytes = IOUtils.toByteArray(inputStream);
                storage.put(templateName, bytes);
                log.info("Template '{}' is loaded successfully", templateName);
            }
        }
    }

    /**
     * Finds template by name in the storage of templates
     *
     * @param name - name of the template
     * @return bytes of template
     */
    public byte[] getTemplateByName(String name) {
        Assert.notNull(name, "templateName must not be null");

        byte[] template = storage.get(name);
        if (template == null) {
            throw new UnsupportedOperationException("template " + name + " does not exist");
        }

        return template;
    }
}
