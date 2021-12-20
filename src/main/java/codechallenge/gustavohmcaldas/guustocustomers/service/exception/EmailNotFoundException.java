package codechallenge.gustavohmcaldas.guustocustomers.service.exception;

public class EmailNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String msg) {
		super(msg);
	}
	
	public EmailNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
