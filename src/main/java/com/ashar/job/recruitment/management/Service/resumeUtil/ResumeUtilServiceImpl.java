package com.ashar.job.recruitment.management.Service.resumeUtil;

import com.ashar.job.recruitment.management.Exception.NullException;
import com.ashar.job.recruitment.management.Exception.ParsingException;
import com.ashar.job.recruitment.management.Service.converter.DocumentConverter;
import com.ashar.job.recruitment.management.Service.llm.LlmService;
import com.ashar.job.recruitment.management.Service.normalization.NormalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ResumeUtilServiceImpl implements ResumeUtilService{
    @Autowired
    @Qualifier("PdfDocumentConverter")
    private DocumentConverter pdfToText;
    @Autowired
    @Qualifier("DocxDocumentConverter")
    private DocumentConverter docToText;
    @Autowired
    private NormalizationService normalizationService;

    @Autowired
    private LlmService llmService;
    @Override
    public String convertDocumentToText(MultipartFile file) throws NullException, ParsingException {
        if (file==null) throw new NullException("Cannot parse and empty file");
        String fileName = file.getOriginalFilename();
        StringBuilder rawText = new StringBuilder();
        if (fileName.endsWith(".pdf")){
            try{
                rawText.append(pdfToText.extractText(file));
            }catch (Exception e){
                throw new ParsingException("Unable to parse the pdf. Be sure to give the correct resume");
            }
        }
        else if (fileName.endsWith(".docx")){
            try {
                rawText.append(docToText.extractText(file));
            }catch(Exception e){
                throw new ParsingException("Unable to parse the docx. Be sure to give the correct resume");
            }
        }
        if (fileName==null) throw new NullException("File name was null. Cannot define the file format. Please try again");

        return normalizationService.cleanText(rawText.toString());
    }

    @Override
    public Double resumeScore(List<String> skillList) {
        return null;
    }

}
