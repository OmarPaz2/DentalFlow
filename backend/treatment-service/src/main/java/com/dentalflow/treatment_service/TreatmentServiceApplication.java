package com.dentalflow.treatment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TreatmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreatmentServiceApplication.class, args);
	}

}
