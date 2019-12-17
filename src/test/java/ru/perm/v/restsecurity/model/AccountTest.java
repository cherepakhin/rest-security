package ru.perm.v.restsecurity.model;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertArrayEquals;

import java.util.Collection;
import java.util.stream.Stream;
import org.junit.Test;

public class AccountTest {

	private final String ROLE1 = "ROLE_1";
	private final String ROLE2 = "ROLE_2";

	@Test
	public void getArrayRoleSimple() {
		Account account = new Account();
		account.setRole(ROLE1 + "," + ROLE2);
		assertArrayEquals(new String[]{ROLE1, ROLE2}, account.getArrayRole());

		account.setRole(ROLE1 + " , " + ROLE2);
		assertArrayEquals(new String[]{ROLE1, ROLE2}, account.getArrayRole());
	}

	@Test
	public void getArrayRoleFromOneString() {
		Account account = new Account();
		account.setRole(ROLE1);
		System.out.println(account.getArrayRole());
		assertArrayEquals(new String[]{ROLE1}, account.getArrayRole());
	}

	@Test
	public void createRoles() {
		Account account = new Account();
		account.setRole(ROLE1 + "," + ROLE2);
		Collection<String> collect = Stream.of(account.getArrayRole()).map(String::new)
				.collect(toList());
		System.out.println(collect);
		assertArrayEquals(new String[]{ROLE1, ROLE2}, collect.toArray());
	}
}