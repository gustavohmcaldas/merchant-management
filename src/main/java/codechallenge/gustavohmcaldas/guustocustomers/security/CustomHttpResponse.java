package codechallenge.gustavohmcaldas.guustocustomers.security;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomHttpResponse {

	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "Canada/Pacific")
	private Date timestamp;
	
	public CustomHttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {

		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.reason = reason;
		this.message = message;
		this.timestamp = new Date();
	}
}
