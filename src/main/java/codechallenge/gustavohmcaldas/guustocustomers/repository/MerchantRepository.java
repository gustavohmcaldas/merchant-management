package codechallenge.gustavohmcaldas.guustocustomers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codechallenge.gustavohmcaldas.guustocustomers.domain.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

	List<Merchant> findByCountry(String firstName);
}
