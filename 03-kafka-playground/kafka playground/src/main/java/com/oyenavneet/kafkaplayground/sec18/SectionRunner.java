package com.oyenavneet.kafkaplayground.sec18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

	public static void main(String[] args) {
		SpringApplication.run(SectionRunner.class,
				"--section=sec18",
				"--config=01-processor"
				);
	}

}
