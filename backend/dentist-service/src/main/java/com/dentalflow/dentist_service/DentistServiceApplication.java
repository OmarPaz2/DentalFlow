package com.dentalflow.dentist_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DentistServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentistServiceApplication.class, args);
	}

}
