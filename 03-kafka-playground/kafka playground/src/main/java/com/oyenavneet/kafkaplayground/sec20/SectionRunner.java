package com.oyenavneet.kafkaplayground.sec20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	/*
		To make producer and consumer work as individual application
		- Created two inner class of Consumer and Producer
		- below is not working in java 21 or more config required
		- create two separate application class

	 */

	@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
	static class Consumer {

		 static void main() {
			SpringApplication.run(
					Consumer.class, "--section=sec03", "--config=01-consumer"
			);
		}

	}

	@SpringBootApplication(scanBasePackages = "com.oyenavneet.kafkaplayground.${section}.consumer")
	static class Producer {

		 static void main() {
			SpringApplication.run(
					Producer.class, "--section=sec03", "--config=02-producer"
			);
		}

	}

}
