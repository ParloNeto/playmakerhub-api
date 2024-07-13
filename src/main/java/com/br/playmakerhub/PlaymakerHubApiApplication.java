package com.br.playmakerhub;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlaymakerHubApiApplication {
	static final Logger logger = LogManager.getLogger(PlaymakerHubApiApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(PlaymakerHubApiApplication.class, args);
	}

}
