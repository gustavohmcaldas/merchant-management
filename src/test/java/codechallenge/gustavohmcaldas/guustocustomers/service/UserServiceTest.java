package codechallenge.gustavohmcaldas.guustocustomers.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import codechallenge.gustavohmcaldas.guustocustomers.repository.UserRepository;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepositoryMock;
	
    @Test
    @DisplayName("loadUserByUsername throws UserNotFoundException when user is not found")
    void loadUserByUsername_UsernameNotFoundException_WhenUserIsNotFound(){
    	
    	BDDMockito.when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(null);
    	
        assertThrows(UsernameNotFoundException.class, () -> {
        	userService.loadUserByUsername(ArgumentMatchers.anyString());
        });
    }
}
