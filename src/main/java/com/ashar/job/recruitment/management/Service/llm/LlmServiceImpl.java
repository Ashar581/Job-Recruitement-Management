package com.ashar.job.recruitment.management.Service.llm;

import com.ashar.job.recruitment.management.Model.QwenChatBody;
import com.ashar.job.recruitment.management.Model.QwenChatSession;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class LlmServiceImpl implements LlmService{
    @Value("${llm.model.url}")
    private String llmBaseUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public Map<String,Object> cvSummaryGenerator(String document) {
        String name = "Ashar";
        String llmChatBotName = "Qwen";

        // Create the inner list as a double array (list of list of strings)
        List<List<String>> innerArray = new ArrayList<>();
        innerArray.add(List.of(name, document + " You are supposed to process the input text and generate a JSON response with the following keys: name,email,phone,primary_skills,projects(name and link if any),summary(it should be in person's name),experience(in year and month keys.). Just reply with JSON that i ask no other replies needed, not text needed too just begin reply through {_json_structure_}"));

        // Now add Qwen at the beginning and end of the inner array
        List<Object> outerArray = new ArrayList<>();
        outerArray.add(llmChatBotName); // First Qwen
        outerArray.add(innerArray);     // Inner double array
        outerArray.add(llmChatBotName); // Last Qwen

        // Set the final data in the chatBody
        QwenChatBody chatBody = new QwenChatBody();
        chatBody.setData(outerArray);

        //call the api
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        QwenChatSession chatEvent = restTemplate.exchange(llmBaseUrl, HttpMethod.POST, new HttpEntity<>(chatBody, httpHeaders), new ParameterizedTypeReference<QwenChatSession>() {
        }).getBody();
        System.out.println(chatEvent.getEvent_id());

        //calling the api for getting the chat response.
        List<Object> chatResponse = restTemplate.execute(
                llmBaseUrl + "/" + chatEvent.getEvent_id(),
                HttpMethod.GET,
                request -> {
                    request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE);
                },
                response -> {
                    InputStream inputStream = response.getBody();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    boolean completed = false;
                    List<Object> data = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.equalsIgnoreCase("event: complete")) {
                            completed = true;
                        }

                        if (line.startsWith("data: ") && completed) {
                            try {
                                data = objectMapper.readValue(line.substring(6), new TypeReference<List<Object>>() {});
                                completed = false;
                            } catch (Exception e) {
                                System.err.println("Failed to parse event data: " + e.getMessage());
                            }
                        }
                    }
                    return data;
                }
        );
        //Traverse the chat response. Get the Qwen Reply.
        List<Object> qwenReply = chatResponse.stream()
                .filter(item -> item instanceof List)
                .map(item -> (List<?>) item)
                .flatMap(List::stream)
                .filter(innerItem -> innerItem instanceof List && ((List<?>) innerItem).size() > 1 && "Qwen".equals(((List<?>) innerItem).get(0)))
                .map(innerItem -> (List<Object>) innerItem)
                .findFirst()
                .orElse(null);
        //for debugging
//        System.out.println("--CV SUMMARY--\n"+qwenReply.getLast());

        Map<String,Object> metadata = new LinkedHashMap<>();
        try{
            metadata = objectMapper.readValue(qwenReply.getLast().toString().substring(qwenReply.getLast().toString().indexOf("{"),qwenReply.getLast().toString().lastIndexOf("}")+1),Map.class);
            metadata.put("docType","CV");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return metadata;
    }
}
