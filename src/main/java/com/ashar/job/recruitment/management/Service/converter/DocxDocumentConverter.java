package com.ashar.job.recruitment.management.Service.converter;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component("DocxDocumentConverter")
public class DocxDocumentConverter implements DocumentConverter{
    @Override
    public String extractText(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XWPFDocument document = new XWPFDocument(inputStream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String rawText = extractor.getText();
        document.close();

        return rawText;
    }
}
