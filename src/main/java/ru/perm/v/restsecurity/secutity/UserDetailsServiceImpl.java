package ru.perm.v.restsecurity.secutity;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(
			String username) throws UsernameNotFoundException {
		Account account = accountRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(account.getUsername(), account.getPassword(), Collections.emptyList());
	}
}
