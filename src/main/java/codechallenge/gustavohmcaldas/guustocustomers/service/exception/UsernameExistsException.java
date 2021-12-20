package codechallenge.gustavohmcaldas.guustocustomers.service.exception;

public class UsernameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameExistsException(String msg) {
		super(msg);
	}
	
	public UsernameExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
