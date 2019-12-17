package ru.perm.v.restsecurity.secutity;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(
			String username) throws UsernameNotFoundException {
		Account account = accountRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		logger.debug("loadUserByUsername:"+account);
		List<SimpleGrantedAuthority> roles = Stream.of(account.getArrayRole())
				.map(SimpleGrantedAuthority::new)
				.collect(toList());
		return new User(account.getUsername(), account.getPassword(), roles);
	}
}
