package ru.perm.v.restsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(value = "/{id}")
	public Account getById(@PathVariable Long id) {
		validate(id);
		logger.info(String.format("ID=%d", id));
		Account account = accountRepository.getOne(id);
		logger.debug("/{id}" + account);
		return account;
	}

	@GetMapping(value = "")
	public Accounts getAll() {
		SecurityContext context = SecurityContextHolder
				.getContext();
		logger.debug("Context.getAuthentication:" + context.getAuthentication());
		return new Accounts(accountRepository.findAll(Sort.by("username")));
	}

	@PutMapping(value = "")
	public Account create(@RequestBody Account account) {
		// Кодирую пароль
		logger.debug("PUT account:" + account);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}


	private void validate(Long id) {
		boolean exist = accountRepository.existsById(id);
		if (!exist) {
			throw new EntityNotFoundException(String.format("Account not found id=%d", id));
		}
	}
}
