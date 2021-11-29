package com.egg.patitas.red;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedApplication.class, args);
	}

}
