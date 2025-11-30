package com.barbatech.natomada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NaTomadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaTomadaApplication.class, args);
	}

}
