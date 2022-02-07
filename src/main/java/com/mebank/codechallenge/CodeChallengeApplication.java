package com.mebank.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CodeChallengeApplication extends ServletInitializer {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CodeChallengeApplication.class, args);
		context.getBean(FileProcessor.class).processFile();
	}

}
