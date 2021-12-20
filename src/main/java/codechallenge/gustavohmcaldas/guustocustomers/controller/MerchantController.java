package codechallenge.gustavohmcaldas.guustocustomers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;
import codechallenge.gustavohmcaldas.guustocustomers.service.MerchantService;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/merchants")
@RequiredArgsConstructor
public class MerchantController {

	private final MerchantService merchantService;

	@GetMapping
	public ResponseEntity<List<Merchant>> findAll() {
		return ResponseEntity.ok(merchantService.findAll());
	}
	
	@GetMapping(path = "/find")
	public ResponseEntity<List<Merchant>> findByCountry(@RequestParam String country) {
		return ResponseEntity.ok(merchantService.findByCountry(country));
	}
}
