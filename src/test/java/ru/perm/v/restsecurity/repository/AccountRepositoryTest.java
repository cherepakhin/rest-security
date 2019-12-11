package ru.perm.v.restsecurity.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.restsecurity.model.Account;

@SpringBootTest
public class AccountRepositoryTest {
	@Autowired
	AccountRepository accountRepository;

	private Long ID=1L;
	final String USERNAME = "USERNAME";
	final String PASSWORD = "PASSWORD";

	@Test
	public void createAccount() {
		Account account = new Account(USERNAME, PASSWORD);
		Account createdAccount = accountRepository.save(account);
		assertEquals(createdAccount.getUsername(), USERNAME);
		assertEquals(createdAccount.getPassword(), PASSWORD);
		assertEquals(createdAccount.getId(),ID);
	}

	@Test
	public void findByIdTest() {
		Account account = accountRepository.save(new Account(USERNAME, PASSWORD));
		assertNotNull(account);
		assertEquals(account.getUsername(), USERNAME);
		assertEquals(account.getPassword(), PASSWORD);
	}

	@Test
	public void findByUsername() {
		Account account = accountRepository.save(new Account(USERNAME, PASSWORD));
		assertNotNull(account);
		account=accountRepository.findByUsername(USERNAME).get();
		assertEquals(account.getUsername(), USERNAME);
		assertEquals(account.getPassword(), PASSWORD);
	}
}