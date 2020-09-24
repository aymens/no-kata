package com.nexeo.kata.bank_account.repo;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.model.Statement;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;

/**
 * Statements repository interface.
 * @author aymens
 *
 */
public interface StatementsRepository extends Repository<Statement> {

	/**
	 * Returns the latest statement of the provided account.
	 * @param account
	 * @return
	 * @throws DataAccessException
	 */
	Statement get(Account account) throws DataAccessException ;
}
