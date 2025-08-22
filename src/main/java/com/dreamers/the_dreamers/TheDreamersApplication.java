package com.dreamers.the_dreamers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheDreamersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheDreamersApplication.class, args);
	}

}
