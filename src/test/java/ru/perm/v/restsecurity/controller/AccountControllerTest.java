package ru.perm.v.restsecurity.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.perm.v.restsecurity.model.Account;
import ru.perm.v.restsecurity.repository.AccountRepository;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	final String USERNAME = "NAME_1";
	final String PASSWORD = "PASSWORD_1";
	final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AccountRepository accountRepository;

	@Test
	public void name() {
		assertTrue(true);
	}

	@Test
	public void getById() throws Exception {
		when(this.accountRepository.getOne(1L)).thenReturn(new Account(USERNAME, PASSWORD));
		ResultActions result = this.mockMvc
				.perform(get("/account/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.bookmarks", hasSize(0)));
	}

	@Test
	public void getAll() throws Exception {
		Account account = new Account(USERNAME, PASSWORD);
		when(this.accountRepository.findAll(Sort.by("username")))
				.thenReturn(Arrays.asList(account, account));
		ResultActions result = this.mockMvc
				.perform(get("/account"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.accounts", hasSize(2)));
	}

	@Test
	public void create() throws Exception {
		Account account = new Account(USERNAME, PASSWORD);
		String jsonAccount = objectMapper.writeValueAsString(account);
		when(this.accountRepository.save(account))
				.thenReturn(account);

		ResultActions result = this.mockMvc
				.perform(put("/account")
						.contentType(contentType)
						.content(jsonAccount))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.bookmarks", hasSize(0)));
	}
}