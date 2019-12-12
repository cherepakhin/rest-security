package ru.perm.v.restsecurity.controller;

import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

@RestController
@RequestMapping("/account")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(value = "/{id}")
	public Account getById(@PathVariable Long id) {
		logger.info(String.format("ID=%d", id));
		Account createdAccount = accountRepository.getOne(id);
		System.out.println(createdAccount);
		return createdAccount;
	}

	@GetMapping(value = "")
	public Collection<Account> getAll() {
		List<Account> accounts = accountRepository.findAll(Sort.by("username"));
		return accounts;
	}

	@PutMapping(value = "")
	public Account create(@RequestBody Account account) {
		Account createdAccount = accountRepository.save(account);
		return createdAccount;
	}
}
