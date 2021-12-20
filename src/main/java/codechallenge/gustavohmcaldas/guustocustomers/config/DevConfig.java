package codechallenge.gustavohmcaldas.guustocustomers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import codechallenge.gustavohmcaldas.guustocustomers.service.DBService;
import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig {

	private final DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean dataBaseInstance() {
		if (strategy.equals("create")) {
			dbService.dataBaseInstance();
		}

		return false;
	}
}
