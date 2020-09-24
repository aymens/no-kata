package com.nexeo.kata.bank_account.app;

import static com.nexeo.kata.bank_account.app.ServicesHelper.accountsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.service.exceptions.ServiceException;

/**
 * Main app
 *
 */
public class App {
	
	private static Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		try {
			accountsService().createAccount(new Account());
			accountsService().createAccount(new Account());
			accountsService().createAccount(new Account(1));
		} catch (ServiceException e) {
			log.error("", e);
		}
	}
}
