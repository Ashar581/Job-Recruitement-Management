package com.ashar.job.recruitment.management.Service.converter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component("PdfDocumentConverter")
public class PdfDocumentConverter implements DocumentConverter{
    @Override
    public String extractText(MultipartFile file) throws IOException {
        InputStream stream = file.getInputStream();
        PDDocument document = PDDocument.load(stream);
        PDFTextStripper stripper = new PDFTextStripper();
        String rawText = stripper.getText(document);
        document.close();
        return rawText;
    }
}
