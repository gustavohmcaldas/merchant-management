package codechallenge.gustavohmcaldas.guustocustomers.service.exception;

public class EmailExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailExistsException(String msg) {
		super(msg);
	}
	
	public EmailExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
