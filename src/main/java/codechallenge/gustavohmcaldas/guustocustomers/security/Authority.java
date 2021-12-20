package codechallenge.gustavohmcaldas.guustocustomers.security;

public class Authority {

	public static final String[] USER_AUTHORITIES = {"user:read"};
	public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:update", "user:create", "user:delete"};
}