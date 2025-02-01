package com.returdev.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerServiceApplication.class, args);
	}

}
