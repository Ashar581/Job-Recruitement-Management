package com.ashar.job.recruitment.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobRecruitmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobRecruitmentApplication.class, args);
	}

}
