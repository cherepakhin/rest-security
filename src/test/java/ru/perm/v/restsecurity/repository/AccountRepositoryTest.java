package ru.perm.v.restsecurity.repository;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.perm.v.restsecurity.RestSecurityApplication;
import ru.perm.v.restsecurity.model.Account;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountRepositoryTest {

	final String USERNAME = "NAME_1";
	final String PASSWORD = "PASSWORD_1";
	private final Long ID = 1L;
	@Autowired
	AccountRepository accountRepository;

	@Test
	public void createAccount() {
		Account account = new Account(USERNAME + "TEST", PASSWORD,"");
		Account createdAccount = accountRepository.save(account);
		assertEquals(createdAccount.getUsername(), USERNAME + "TEST");
		assertEquals(createdAccount.getPassword(), PASSWORD);
		assertTrue(createdAccount.getId() > 0);
	}

	@Test
	public void findByIdTest() {
		Account account = accountRepository.getOne(ID);
		assertNotNull(account);
		assertEquals(account.getUsername(), USERNAME);
		assertEquals(account.getPassword(), PASSWORD);
	}

	@Test
	public void findByUsername() {
		Optional<Account> optionalAccount = accountRepository.findByUsername(USERNAME);
		Account account = optionalAccount.orElseGet(Account::new);
		assertNotNull(account);
		assertEquals(account.getId(), ID);
		assertEquals(account.getUsername(), USERNAME);
		assertEquals(account.getPassword(), PASSWORD);
	}
}