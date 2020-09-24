package com.nexeo.kata.bank_account.app.conf;

import com.google.inject.AbstractModule;
import com.nexeo.kata.bank_account.repo.AccountsRepository;
import com.nexeo.kata.bank_account.repo.StatementsRepository;
import com.nexeo.kata.bank_account.repo.TransactionsRepository;
import com.nexeo.kata.bank_account.repo.impl.in_memory.AccountsRepositoryInMemory;
import com.nexeo.kata.bank_account.repo.impl.in_memory.StatementsRepositoryInMemory;
import com.nexeo.kata.bank_account.repo.impl.in_memory.TransactionsRepositoryInMemory;
import com.nexeo.kata.bank_account.service.AccountsService;

public class MainModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(AccountsRepository.class).to(AccountsRepositoryInMemory.class).asEagerSingleton();
		bind(StatementsRepository.class).to(StatementsRepositoryInMemory.class).asEagerSingleton();
		bind(TransactionsRepository.class).to(TransactionsRepositoryInMemory.class).asEagerSingleton();
		bind(AccountsService.class);
	}
}
