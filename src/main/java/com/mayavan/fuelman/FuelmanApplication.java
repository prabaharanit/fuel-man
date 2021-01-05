package com.mayavan.fuelman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FuelmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelmanApplication.class, args);
	}

}
