package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.Repository")
@EntityScan(basePackages = "com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.model")
public class KabaddiLiveScoreBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(KabaddiLiveScoreBoardApplication.class, args);
	}

}
