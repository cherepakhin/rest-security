package ru.perm.v.restsecurity.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.model.Bookmark;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookmarkRepositoryTest {

	final String USERNAME = "USERNAME";
	final String PASSWORD = "PASSWORD";
	final String URI = "URI";
	final String DESCRIPTION = "DESCRIPTION";
	final private Long ID = 1L;

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	BookmarkRepository bookmarkRepository;

	@Test
	public void createTest() {
		Account account = accountRepository.save(new Account(USERNAME, PASSWORD,""));
		Bookmark bookmark = bookmarkRepository.save(new Bookmark(account, URI, DESCRIPTION));
		assertNotNull(bookmark);
		assertEquals(bookmark.getUri(), URI);
		assertEquals(bookmark.getDescription(), DESCRIPTION);
		System.out.println(bookmark);
	}
}