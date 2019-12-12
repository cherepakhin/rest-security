package ru.perm.v.restsecurity.controller;

import java.util.ArrayList;
import java.util.List;
import ru.perm.v.restsecurity.model.Account;

public class Accounts {
	private List<Account> accounts;

	public Accounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
