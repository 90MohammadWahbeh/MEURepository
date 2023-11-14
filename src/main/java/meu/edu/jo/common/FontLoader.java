package meu.edu.jo.common;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
public class FontLoader {
    private final ResourceLoader resourceLoader;
    private static BaseFont customFont;
    private static String customFontPath;

    public FontLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public void loadFont() {
        try {
            Resource resource = resourceLoader.getResource("classpath:times.ttf");
            Path fontPath = Files.createTempFile("times", ".ttf");
            Files.copy(resource.getInputStream(), fontPath, StandardCopyOption.REPLACE_EXISTING);
            customFontPath = fontPath.toString();
            customFont = BaseFont.createFont(customFontPath, BaseFont.IDENTITY_H, true);

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public static BaseFont getCustomFont() {
        return customFont;
    }

    public static String getCustomFontPath() {
        return customFontPath;
    }
}


