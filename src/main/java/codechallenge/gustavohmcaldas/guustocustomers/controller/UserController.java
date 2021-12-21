package codechallenge.gustavohmcaldas.guustocustomers.controller;

import javax.mail.MessagingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codechallenge.gustavohmcaldas.guustocustomers.constant.SecurityConstant;
import codechallenge.gustavohmcaldas.guustocustomers.controller.exception.ResponseEntityExceptionHandler;
import codechallenge.gustavohmcaldas.guustocustomers.domain.User;
import codechallenge.gustavohmcaldas.guustocustomers.security.JWTTokenProvider;
import codechallenge.gustavohmcaldas.guustocustomers.security.UserPrincipal;
import codechallenge.gustavohmcaldas.guustocustomers.service.UserService;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.EmailExistsException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UserNotFoundException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UsernameExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController extends ResponseEntityExceptionHandler {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JWTTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		authenticate(user.getUsername(), user.getPassword());
		User loginUser = userService.findUserByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		
		return new ResponseEntity<>(loginUser,jwtHeader, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistsException, EmailExistsException, MessagingException {
		User newUser = userService.register(user.getUsername(), user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);		
	}

	private HttpHeaders getJwtHeader(UserPrincipal user) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
		return headers;
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
