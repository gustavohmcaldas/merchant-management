package codechallenge.gustavohmcaldas.guustocustomers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;
import codechallenge.gustavohmcaldas.guustocustomers.repository.MerchantRepository;
import codechallenge.gustavohmcaldas.guustocustomers.service.MerchantService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantService {

	private final MerchantRepository merchantRepository;

	public List<Merchant> findAll() {
		return merchantRepository.findAll();
	}
	
	public List<Merchant> findByCountry(String country) {
		return merchantRepository.findByCountry(country);
	}
}
