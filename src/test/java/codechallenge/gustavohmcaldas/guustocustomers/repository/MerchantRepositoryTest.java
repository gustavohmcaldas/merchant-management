package codechallenge.gustavohmcaldas.guustocustomers.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;
import codechallenge.gustavohmcaldas.guustocustomers.util.MerchantCreator;

@DataJpaTest
@DisplayName("Tests for Merchant Repository")
class MerchantRepositoryTest {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Test
	@DisplayName("Find by FirstName returns list of student when successful")
	void findByFirstName_ReturnsListOfStudent_WhenSuccessful() {
		Merchant merchantToBeSaved = MerchantCreator.createMerchantToBeSaved();
		
		Merchant savedMerchant = this.merchantRepository.save(merchantToBeSaved);
		
		String country = savedMerchant.getCountry();
		
		List<Merchant> merchants = this.merchantRepository.findByCountry(country);
		
		Assertions.assertThat(merchants)
				.isNotEmpty()
				.contains(savedMerchant);
	}
	
	@Test
	@DisplayName("findByCountry returns empty list no student is not found")
	void findByCountry_ReturnsEmptyyList_WhenCountryIsNotFound() {
		List<Merchant> merchants = this.merchantRepository.findByCountry("Brazil");
		
		Assertions.assertThat(merchants).isEmpty();
	}
}
