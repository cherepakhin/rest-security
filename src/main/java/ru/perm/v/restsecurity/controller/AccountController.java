package ru.perm.v.restsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public Account create(@PathVariable(name = "username") String username,
//			@PathVariable(name = "password") String password) {
//		Account createdAccount = accountRepository.save(new Account(username, password));
//		return createdAccount;
//	}

	@GetMapping(value = "/{id}")
	public Account getById(@PathVariable Long id) {
		logger.info(String.format("ID=%d", id));
		Account createdAccount = accountRepository.getOne(id);
		System.out.println(createdAccount);
		return createdAccount;
	}

	@GetMapping(value = "/")
	public Account create() {
		Account createdAccount;
		createdAccount = accountRepository.save(new Account("username", "password"));
		return createdAccount;
	}
}
