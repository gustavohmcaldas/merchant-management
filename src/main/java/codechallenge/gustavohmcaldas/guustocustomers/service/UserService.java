package codechallenge.gustavohmcaldas.guustocustomers.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import codechallenge.gustavohmcaldas.guustocustomers.constant.UserConstant;
import codechallenge.gustavohmcaldas.guustocustomers.domain.User;
import codechallenge.gustavohmcaldas.guustocustomers.enumeration.Role;
import codechallenge.gustavohmcaldas.guustocustomers.repository.UserRepository;
import codechallenge.gustavohmcaldas.guustocustomers.security.UserPrincipal;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.EmailExistsException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UserNotFoundException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UsernameExistsException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@Qualifier("UserDetailsService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final LoginAttemptService loginAttemptService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findUserByUsername(username);
		
		if(user == null) {
			LOGGER.error(UserConstant.NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(UserConstant.NO_USER_FOUND_BY_USERNAME + username);
		} else {
			validateLoginAttempt(user);
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(UserConstant.FOUND_USER_BY_USERNAME + username);
			return userPrincipal;
		}		
	}
	
	private void validateLoginAttempt(User user) {
		if(user.isNotLocked()) {
			if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	public User register(String username, String email) throws UserNotFoundException, UsernameExistsException, EmailExistsException, MessagingException {
		validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
		
		User user = new User();
		
		//String password = generatePassword();
		String password = "123456";
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(encodePassword(password));
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		userRepository.save(user);
		return user;
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) 
			throws UserNotFoundException, UsernameExistsException, EmailExistsException {
		
		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmail = findUserByEmail(newEmail);
		
		if(StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);
			if(currentUser == null) {
				throw new UserNotFoundException(UserConstant.NO_USER_FOUND_BY_USERNAME + currentUsername);
			}			
			
			if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
				throw new UsernameExistsException(UserConstant.USERNAME_ALREADY_EXISTS);
			}
						
			if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistsException(UserConstant.EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		} else {
			if(userByNewUsername != null) {
				throw new UsernameExistsException(UserConstant.USERNAME_ALREADY_EXISTS);
			}
			
			if(userByNewEmail != null) {
				throw new EmailExistsException(UserConstant.EMAIL_ALREADY_EXISTS);
			}
			
			return null;
		}
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
}
