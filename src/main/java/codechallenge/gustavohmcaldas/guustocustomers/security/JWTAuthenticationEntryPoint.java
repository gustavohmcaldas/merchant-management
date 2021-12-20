package codechallenge.gustavohmcaldas.guustocustomers.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import codechallenge.gustavohmcaldas.guustocustomers.constant.SecurityConstant;

@Component
public class JWTAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException {
		
		CustomHttpResponse httpResponse = new CustomHttpResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), SecurityConstant.FORBIDDEN_MESSAGE);
	
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
