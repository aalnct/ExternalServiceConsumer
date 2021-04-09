package com.external.service.consumer.ExternalServiceConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ExternalServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalServiceConsumerApplication.class, args);
	}

}
