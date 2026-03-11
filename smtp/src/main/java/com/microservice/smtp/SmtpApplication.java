package com.microservice.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmtpApplication.class, args);
	}

}
