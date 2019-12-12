package ru.perm.v.restsecurity.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(value = "/{id}")
	public Account getById(@PathVariable Long id) {
		logger.info(String.format("ID=%d", id));
		Account account = accountRepository.getOne(id);
		System.out.println(account);
		return account;
	}

	@GetMapping(value = "")
	public Accounts getAll() {
		return new Accounts(accountRepository.findAll(Sort.by("username")));
	}

	@PutMapping(value = "")
	public Account create(@RequestBody Account account) {
		return accountRepository.save(account);
	}
}
