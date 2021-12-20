package codechallenge.gustavohmcaldas.guustocustomers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import codechallenge.gustavohmcaldas.guustocustomers.service.DBService;
import lombok.RequiredArgsConstructor;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

	private final DBService dbService;

	@Bean
	public void dataBaseInstance() {
		dbService.dataBaseInstance();
	}
}
