package com.nexeo.kata.bank_account.repo.impl.in_memory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.model.Transaction;
import com.nexeo.kata.bank_account.repo.TransactionsRepository;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;

public class TransactionsRepositoryInMemory extends RepositoryInMemory<Transaction> implements TransactionsRepository {

	@Override
	public Transaction add(Transaction entity) throws DataAccessException {
		Transaction result = super.add(entity);
		/*
		 * Sort entries by descending date asynchronously to keep the repository#get() simple and fast in this "poc" mode.
		 * In a real world app we'd have a database and fetching sorted results won't be an issue.
		 */
		CompletableFuture.runAsync(() -> {
			entities.sort((tx1, tx2) -> tx1.getDate().compareTo(tx2.getDate()));
		});
		return result;
	}
	
	@Override
	public List<Transaction> get(Account account, int offset, int size) throws DataAccessException {
		//TODO perform necessary args validation 
		/*
		 * Here we suppose account exists and that offset is coherent with current data volume. 
		 */
		
		return entities.stream().filter(tx -> tx.getAccount().getId() == account.getId()).skip(offset).limit(size)
				.map(SerializationUtils::clone).collect(Collectors.toList());
	}
}
