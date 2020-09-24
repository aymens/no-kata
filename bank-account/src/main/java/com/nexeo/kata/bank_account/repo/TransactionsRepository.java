package com.nexeo.kata.bank_account.repo;

import java.util.List;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.model.Transaction;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;

/**
 * Transactions repository interface.
 * @author aymens
 *
 */
public interface TransactionsRepository extends Repository<Transaction> {

	List<Transaction> get(Account account, int offset, int size) throws DataAccessException;
}
