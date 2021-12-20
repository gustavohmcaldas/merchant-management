package codechallenge.gustavohmcaldas.guustocustomers.service;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;
import codechallenge.gustavohmcaldas.guustocustomers.repository.MerchantRepository;
import codechallenge.gustavohmcaldas.guustocustomers.util.MerchantCreator;

@ExtendWith(SpringExtension.class)
class MerchantServiceTest {

	@InjectMocks
	private MerchantService merchantService;

	@Mock
	private MerchantRepository merchantRepositoryMock;
	
	@BeforeEach
	void setup() {
		BDDMockito.when(merchantRepositoryMock.findByCountry(ArgumentMatchers.anyString()))
		.thenReturn(List.of(MerchantCreator.createValidMerchant()));
	}
	
	@Test
	@DisplayName("findByCountry returns a list of merchants when successful")
	void findByCountry_ReturnsListOfMerchants_WhenSuccessful() {
		String expectedCountry = MerchantCreator.createValidMerchant().getCountry();

		List<Merchant> merchants = merchantService.findByCountry("Canada");

		Assertions.assertThat(merchants)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);

		Assertions.assertThat(merchants.get(0).getCountry()).isEqualTo(expectedCountry);
	}
	
	@Test
	@DisplayName("findByCountry returns an empty list of merchant when country is not found")
	void findByCountry_ReturnsEmptyListOfMerchant_WhenCountryIsNotFound() {
		BDDMockito.when(merchantRepositoryMock.findByCountry(ArgumentMatchers.anyString()))
				.thenReturn(Collections.emptyList());

		List<Merchant> merchants = merchantService.findByCountry("Brazil");

		Assertions.assertThat(merchants)
				.isNotNull()
				.isEmpty();
	}

}
