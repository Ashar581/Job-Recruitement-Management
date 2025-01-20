package com.ashar.job.recruitment.management.Util;

import com.ashar.job.recruitment.management.Entity.JobOpening;
import com.ashar.job.recruitment.management.Exception.ConflictException;
import com.ashar.job.recruitment.management.Exception.NotFoundException;
import com.ashar.job.recruitment.management.Repository.JobOpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ScoreSvc {
    @Autowired
    private SynonymsSvc synonymsSvc;
    @Autowired
    private JobOpeningRepository jobOpeningRepository;
    public Double scoreGenerator(Map<String,Object> metadata, String jobCode){
        JobOpening applied = jobOpeningRepository.findByCode(jobCode)
                .orElseThrow(()->new NotFoundException("No Job was found."));
        double skillScore = 0.0;
        //set of required skills for the job
        Set<String> requiredSkills = applied.getRequiredSkills()==null?new HashSet<>():new HashSet<>(applied.getRequiredSkills())
                .stream()
                .flatMap(skill -> {
                    Set<String> variants = new HashSet<>();
                    String lowerCaseSkill = skill.toLowerCase();
                    variants.add(lowerCaseSkill);
                    return variants.stream();
                })
                .collect(Collectors.toSet());
        int requiredSkillsSize = requiredSkills.size();

        //set of additional skills for the job opening
        Set<String> goodToHaveSkills = applied.getAdditionalSkills()==null?new HashSet<>():new HashSet<>(applied.getAdditionalSkills()).stream()
                .flatMap(skill -> {
                    Set<String> variants = new HashSet<>();
                    String lowerCaseSkill = skill.toLowerCase();
                    variants.add(lowerCaseSkill);
                    return variants.stream();
                })
                .collect(Collectors.toSet());
        int goodToHaveSkillsSize = goodToHaveSkills.size();

        //primary skills - 70 additional skills - 20 grace for still left skills - 10

        //current applicant's skill sets - also adding additional skill sets by removing the spaces in between of words
        //for better word search.
        List<String> primarySkills = ((List<String>) metadata.get("primary_skills"))
                .stream()
                .flatMap(skill -> {
                    Set<String> variants = new HashSet<>();
                    String lowerCaseSkill = skill.toLowerCase();
                    variants.add(lowerCaseSkill);
                    if (lowerCaseSkill.contains(" ")) {
                        variants.add(lowerCaseSkill.replace(" ", ""));
                    }
                    return variants.stream();
                })
                .collect(Collectors.toList());

        //change the current skills to key(skill) and val(boolean=false(default))
        //for keeping checks on what skills have been checked already.
        Map<String,Boolean> skillsCheck = Stream.concat(requiredSkills.stream(),goodToHaveSkills.stream())
                .collect(Collectors.toMap(skill -> skill,skill -> false));

        //check for required skill
        int requiredSkillsCounter = 0;
        for (String primary : primarySkills){
            Set<String> similarWords = synonymsSvc.getSynonyms(primary);
            if (requiredSkills.contains(primary) && (skillsCheck.get(primary)!=null && !skillsCheck.get(primary))){
                requiredSkillsCounter++;
                skillsCheck.put(primary.toLowerCase(),true);
            }
            for (String similar : similarWords){
                if (requiredSkills.contains(similar.toLowerCase()) && (skillsCheck.get(primary)!=null && !skillsCheck.get(similar))){
                    requiredSkillsCounter++;
                    skillsCheck.put(primary.toLowerCase(),true);
                    skillsCheck.put(similar.toLowerCase(),true);
                }
            }
        }
        double requiredSkillsPoints = ((double) requiredSkillsCounter/(double) requiredSkillsSize)*0.7;
        skillScore+=((double) requiredSkillsCounter/(double) requiredSkillsSize)*0.7;

        //points for goodToHave
        int goodToHaveSkillsCounter = 0;
        for (String primary : primarySkills){
            Set<String> similarWords = synonymsSvc.getSynonyms(primary);
            if (goodToHaveSkills.contains(primary) && (skillsCheck.get(primary)!=null && !skillsCheck.get(primary))){
                goodToHaveSkillsCounter++;
                skillsCheck.put(primary.toLowerCase(),true);
            }
            for (String similar : similarWords){
                if (goodToHaveSkills.contains(similar.toLowerCase()) && (skillsCheck.get(similar)!=null && !skillsCheck.get(similar))){
                    goodToHaveSkillsCounter++;
                    skillsCheck.put(similar.toLowerCase(),true);
                }
            }
        }
        double additionalSkillsPoints = ((double)goodToHaveSkillsCounter/(double) goodToHaveSkillsSize)*0.2;
        skillScore+=((double)goodToHaveSkillsCounter/(double) goodToHaveSkillsSize)*0.2;

        //grace points
        if (((List<?>) metadata.get("primary_skills")).size()>=3){
            skillScore+=0.10;
        }else{
            skillScore += (0.10)/((List<?>) metadata.get("primary_skills")).size();
        }
        System.out.println("Total Skill Score Including Grace points = "+skillScore*100.0);

        //Now score for experience
        double experienceScore = 0.0;
        int candidateExpYears = (int) ((Map<String, Object>) metadata.get("experience")).get("years");
        int candidateExpMonths = (int) ((Map<String, Object>) metadata.get("experience")).get("months");
        if (applied.getPreferredExperience()==0.0){
            experienceScore = 0.8;
            if (candidateExpMonths>0 || candidateExpYears>0){
                experienceScore+=0.2;
            }
        }else {
            int preferredExperienceInMonths = (int) (applied.getPreferredExperience() * 12);
            if (candidateExpMonths>0 || candidateExpYears>0) {
                double candidateExperienceInMonths = (candidateExpYears * 12) + candidateExpMonths;
                experienceScore = (candidateExperienceInMonths / preferredExperienceInMonths) * 0.8;
                if (candidateExpMonths > preferredExperienceInMonths) {
                    experienceScore += 0.2;
                }
            }
        }
        System.out.println("Experience Score in %: " + experienceScore * 100.0);

        //Now score for project
        double projectScore = 0.0;
        int numberOfProjects = ((List<?>)metadata.get("projects")).size();
        if (numberOfProjects>0){
            projectScore = 1.0;
        }
        System.out.println("Project Score: "+projectScore*100.0);
//        System.out.println("Average Points: "+((skillScore*0.60)+(experienceScore*0.25)+(projectScore*0.15))*100.0);
        double totalScore = ((skillScore*0.60)+(experienceScore*0.25)+(projectScore*0.15))*100.0;
        System.out.println(totalScore);
        totalScore = Math.round(totalScore*100.0)/100.0;
        System.out.println("Total Score: "+totalScore);
        return totalScore;
    }
}
