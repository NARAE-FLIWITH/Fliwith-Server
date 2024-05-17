package com.narae.fliwith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FliwithApplication {

	public static void main(String[] args) {
		SpringApplication.run(FliwithApplication.class, args);
	}

}
