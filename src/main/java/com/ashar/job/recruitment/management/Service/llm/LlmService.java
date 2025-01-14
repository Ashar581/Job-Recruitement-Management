package com.ashar.job.recruitment.management.Service.llm;

import java.util.Map;

public interface LlmService {
    Map<String,Object> cvSummaryGenerator(String document);
}
