package com.ebito.document_generator.template;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.net.URL;

/**
 * provides, configures and loads font for form
 */
@Component
@Slf4j
public class FontProvider implements IFontProvider {

    private String fontPath;

    @SneakyThrows
    @PostConstruct
    private void loadFont() {
        final String FONT_NAME = "calibri.ttf";
        URL font = getClass().getClassLoader().getResource("fonts/" + FONT_NAME);
        Assert.notNull(font, "must not be null");
        fontPath = font.toString();
    }

    @SneakyThrows
    @Override
    public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {
        BaseFont fontPrototype = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(fontPrototype);
        font.setSize(size);
        font.setColor(color);
        if (style == Font.BOLD) {
            font.setStyle(Font.BOLD);
        }

        return font;
    }
}
