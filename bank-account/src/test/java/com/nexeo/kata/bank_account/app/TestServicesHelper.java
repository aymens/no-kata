package com.nexeo.kata.bank_account.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nexeo.kata.bank_account.app.cfg.TestMainModule;
import com.nexeo.kata.bank_account.service.AccountsService;

public class TestServicesHelper {

	private static Injector injector = Guice.createInjector(new TestMainModule());

	public static AccountsService accountsService() {
		return injector.getInstance(AccountsService.class);
	}
}
