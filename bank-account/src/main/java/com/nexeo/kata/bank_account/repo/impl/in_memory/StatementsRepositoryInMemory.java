package com.nexeo.kata.bank_account.repo.impl.in_memory;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.SerializationUtils;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.model.Statement;
import com.nexeo.kata.bank_account.repo.StatementsRepository;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;
import com.nexeo.kata.bank_account.repo.exceptions.NotFoundEntityException;

public class StatementsRepositoryInMemory extends RepositoryInMemory<Statement>implements StatementsRepository {

	@Override
	public Statement add(Statement entity) throws DataAccessException {
		Statement result = super.add(entity);
		/*
		 * Sort entries by descending date asynchronously to keep the repository#get() simple and fast in this "poc" mode.
		 * In a real world app we'd have a database and fetching sorted results won't be an issue.
		 */
		CompletableFuture.runAsync(() -> {
			entities.sort((st1, st2) -> st1.getDate().compareTo(st2.getDate()));
		});
		return result;
	}

	@Override
	public Statement get(Account account) throws DataAccessException {
		return entities.stream().filter(s -> s.getAccount().getId() == account.getId())
				.map(SerializationUtils::clone).findFirst()
				/* already sorted by descending date order */.orElseThrow(() -> new NotFoundEntityException());
	}
}
