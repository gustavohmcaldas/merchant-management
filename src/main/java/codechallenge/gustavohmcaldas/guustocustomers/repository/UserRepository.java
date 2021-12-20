package codechallenge.gustavohmcaldas.guustocustomers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codechallenge.gustavohmcaldas.guustocustomers.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByUsername(String username);
	
	User findUserByEmail(String email);
}
