package com.ebito.document_generator.util;

//import com.ebito.document_generator.model.DocumentType;
import com.ebito.document.DocumentType;
import com.sun.star.util.FileIOException;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Class contains functions to process and configure form generating
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FormUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

    /**
     * Converts docx file to pdf one
     *
     * @param docxBuffer   - contains bytes of docx file
     * @param fontProvider - describes styles for new pdf file
     * @return buffer which contains converted pdf file
     * @throws FileIOException when server cannot convert docx file
     */
    public byte[] convertDocxToPdf(byte[] docxBuffer, IFontProvider fontProvider) throws FileIOException {
        Assert.notNull(docxBuffer, "docxBuffer must not be null");
        Assert.notNull(fontProvider, "fontProvider must not be null");

        try (InputStream inputStream = new ByteArrayInputStream(docxBuffer);
             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            XWPFDocument docx = new XWPFDocument(inputStream);
            PdfOptions options = PdfOptions.create();
            options.fontProvider(fontProvider);
            docx.createNumbering();
            PdfConverter.getInstance().convert(docx, pdfOutputStream, options);

            return pdfOutputStream.toByteArray();
        } catch (Exception exception) {
            log.error("Converting docx to pdf problem", exception);
            throw new FileIOException("Error to convert a docx file", exception);
        }
    }

    /**
     * Generates unique name for a pdf file
     *
     * @return unique filename with pdf extension
     */
    public String buildRandomPdfFileName(final DocumentType docType) {
        String filename = RandomStringUtils.randomAlphanumeric(12);
        Date date = new Date();
        String prefix = formatter.format(date);
        String extension = docType == null ? DocumentType.PDF.getDocumentExtension() : DocumentType.DOCX.getDocumentExtension();

        return "created_" + prefix + "_" + filename + extension;
    }
}
