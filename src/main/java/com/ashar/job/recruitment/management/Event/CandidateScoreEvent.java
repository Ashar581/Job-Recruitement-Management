package com.ashar.job.recruitment.management.Event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateScoreEvent {
    private UUID uuid;
    private Map<String,Object> metadata;
    private String jobCode;
}
