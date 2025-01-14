package com.ashar.job.recruitment.management.Service.normalization;

import org.springframework.stereotype.Service;

@Service
public class NormalizationServiceImpl implements NormalizationService{
    @Override
    public String cleanText(String rawText) {
        // Replace tabs and other whitespace characters with a single space
        rawText = rawText.replaceAll("\\s+", " ");

        // Ensure only a single \n between lines
        rawText = rawText.replaceAll("\\n+", "\n");

        // Remove unwanted characters, keeping only alphanumerics, basic punctuation, and spaces
        rawText = rawText.replaceAll("[^\\w\\s@.,;:]", "");

        // Trim whitespace and normalize spaces around the text
        rawText = rawText.trim();

        // Split into lines, trim each line, and reassemble
        String[] lines = rawText.split("\n");
        StringBuilder cleanText = new StringBuilder();

        for (String line : lines) {
            cleanText.append(line.trim()).append("\n");
        }

        // Return the cleaned text, ensuring no trailing newlines
        return cleanText.toString().trim();
    }
}
