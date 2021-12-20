package codechallenge.gustavohmcaldas.guustocustomers.controller.exception;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;

import codechallenge.gustavohmcaldas.guustocustomers.security.CustomHttpResponse;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.EmailExistsException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.EmailNotFoundException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UserNotFoundException;
import codechallenge.gustavohmcaldas.guustocustomers.service.exception.UsernameExistsException;

@RestControllerAdvice
public class ResponseEntityExceptionHandler implements ErrorController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
	private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
	private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again.";
	private static final String ACCOUNT_DISABLED = "Your account has beed disabled. If this is an error, please contact administration";
	private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
	private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";

	@ExceptionHandler(DisabledException.class)	
	public ResponseEntity<CustomHttpResponse> accountDisabledException() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)	
	public ResponseEntity<CustomHttpResponse> badCredentialsException() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
	}
	
	@ExceptionHandler(AccessDeniedException.class)	
	public ResponseEntity<CustomHttpResponse> accessDeniedException() {
		return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
	}
	
	@ExceptionHandler(LockedException.class)	
	public ResponseEntity<CustomHttpResponse> lockedException() {
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
	}
	
	@ExceptionHandler(TokenExpiredException.class)	
	public ResponseEntity<CustomHttpResponse> tokenExpiredException(TokenExpiredException exception) {
		return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}
	
	@ExceptionHandler(EmailExistsException.class)	
	public ResponseEntity<CustomHttpResponse> emailExistsException(EmailExistsException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(UsernameExistsException.class)	
	public ResponseEntity<CustomHttpResponse> usernameExistsException(UsernameExistsException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)		
	public ResponseEntity<CustomHttpResponse> emailNotFoundException(EmailNotFoundException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)		
	public ResponseEntity<CustomHttpResponse> userNotFoundException(UserNotFoundException exception) {
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
//	@ExceptionHandler(NoHandlerFoundException.class)		
//	public ResponseEntity<CustomHttpResponse> methodNotSupportedException(NoHandlerFoundException exception) {
//		return createHttpResponse(HttpStatus.BAD_REQUEST, "This page was not found");
//	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)		
	public ResponseEntity<CustomHttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods().iterator().next());
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED,  supportedMethod));
	}
	
	@ExceptionHandler(Exception.class)		
	public ResponseEntity<CustomHttpResponse> internalServerErrorException(Exception exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(NoResultException.class)		
	public ResponseEntity<CustomHttpResponse> notFoundException(NoResultException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}
	
	@ExceptionHandler(IOException.class)		
	public ResponseEntity<CustomHttpResponse> ioException(IOException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}

	private ResponseEntity<CustomHttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new CustomHttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message.toUpperCase()), httpStatus);
	}
}
