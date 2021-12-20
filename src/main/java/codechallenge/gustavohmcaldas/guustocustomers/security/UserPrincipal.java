package codechallenge.gustavohmcaldas.guustocustomers.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import codechallenge.gustavohmcaldas.guustocustomers.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private final User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isNotLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}
}
