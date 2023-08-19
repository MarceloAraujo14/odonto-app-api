package br.com.odontoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OdontoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdontoAppApplication.class, args);
	}

}
