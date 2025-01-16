package com.ashar.job.recruitment.management.Util;

import com.ashar.job.recruitment.management.Exception.FailedProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class SynonymsSvc {
    @Value("${dataMuseBaseUrl}")
    private String dataMuseBaseUrl;
    @Autowired
    private RestTemplate restTemplate;
    public List<String> getSynonyms(String word){
        try {
            List<Map<String, Object>> synonymsBody = restTemplate.exchange(dataMuseBaseUrl + word, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String,Object>>>() {}).getBody();
            return synonymsBody.stream()
                    .map(syn -> syn.get("word").toString())
                    .toList();
        }catch (HttpStatusCodeException e){
            throw new FailedProcessException(e.getMessage());
        }
    }
}
