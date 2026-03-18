package com.oyenavneet.kafkaplayground.sec01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionRunner {

	public static void main(String[] args) {
		SpringApplication.run(SectionRunner.class,
				"--section=sec01",
				"--config=04-multiple-topics"
				);
	}

}
