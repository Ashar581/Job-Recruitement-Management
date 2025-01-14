package com.ashar.job.recruitment.management.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QwenChatBody {
    private List<Object> data;
//    private List<QwenInnerBody> data;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QwenInnerBody{
        @JsonValue
        private String chatBotName;
        @JsonValue
        private List<List<String>> usernameAndPrompt;
        @JsonValue
        private String chatBotNameEnd;
    }
}
