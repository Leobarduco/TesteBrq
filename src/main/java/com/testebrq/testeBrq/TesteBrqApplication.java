package com.testebrq.testeBrq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TesteBrqApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteBrqApplication.class, args);
	}

}
