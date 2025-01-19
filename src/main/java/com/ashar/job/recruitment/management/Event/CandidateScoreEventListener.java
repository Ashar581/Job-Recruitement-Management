package com.ashar.job.recruitment.management.Event;

import com.ashar.job.recruitment.management.Dto.CandidateDto;
import com.ashar.job.recruitment.management.Service.candidate.CandidateService;
import com.ashar.job.recruitment.management.Util.ScoreSvc;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
public class CandidateScoreEventListener {
    Log log = LogFactory.getLog(CandidateScoreEventListener.class);
    @Autowired
    private ScoreSvc scoreSvc;
    @Autowired
    private CandidateService candidateService;
    @TransactionalEventListener
    @Async
    public void handleCandidateScoreEvent(CandidateScoreEvent candidateScoreEvent){
        log.info("Event triggered for Candidate: "+candidateScoreEvent.getMetadata().get("name")+" at "+ Date.from(Instant.now())+" for score generation.");
        double totalScore = scoreSvc.scoreGenerator(candidateScoreEvent.getMetadata(),candidateScoreEvent.getJobCode());
        CandidateDto dto = new CandidateDto();
        dto.setUuid(candidateScoreEvent.getUuid());
        dto.setScore(totalScore);
        candidateService.update(dto);
    }
}
