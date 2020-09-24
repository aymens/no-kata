package com.nexeo.kata.bank_account.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nexeo.kata.bank_account.app.conf.MainModule;
import com.nexeo.kata.bank_account.service.AccountsService;

public class ServicesHelper {

	private static Injector injector = Guice.createInjector(new MainModule());

	public static AccountsService accountsService() {
		return injector.getInstance(AccountsService.class);
	}
}
