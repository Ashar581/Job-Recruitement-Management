package com.ashar.job.recruitment.management.Util;

import com.ashar.job.recruitment.management.Exception.FailedProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SynonymsSvc {
    @Value("${dataMuseBaseUrl}")
    private String dataMuseBaseUrl;
    @Autowired
    private RestTemplate restTemplate;
    public Set<String> getSynonyms(String word){
        if (word==null || word.isEmpty()) return new HashSet<>();
        try {
            List<Map<String, Object>> synonymsBody = restTemplate.exchange(dataMuseBaseUrl + word, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String,Object>>>() {}).getBody();
            return synonymsBody.stream()
                    .map(syn -> syn.get("word").toString().trim())
                    .collect(Collectors.toSet());
        }catch (HttpStatusCodeException e){
            throw new FailedProcessException(e.getMessage());
        }
    }
}
