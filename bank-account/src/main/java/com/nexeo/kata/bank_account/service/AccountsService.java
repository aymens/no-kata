package com.nexeo.kata.bank_account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.apache.commons.lang3.StringUtils.rightPad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.model.Statement;
import com.nexeo.kata.bank_account.model.Transaction;
import com.nexeo.kata.bank_account.repo.AccountsRepository;
import com.nexeo.kata.bank_account.repo.StatementsRepository;
import com.nexeo.kata.bank_account.repo.TransactionsRepository;
import com.nexeo.kata.bank_account.repo.enums.Operation;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;
import com.nexeo.kata.bank_account.service.exceptions.ServiceException;

/**
 * Accounts operations services
 * 
 * TODO do all necessary validity checks beforehand for all methods, arguments and amounts
 * Objects.requireNonNull(account, "account should not be null");
 * Objects.requireNonNull(account.getId(), "account.id should not be null");
 * Then, when something's wrong, throw the appropriate exceptions.
 * 		 
 * @author aymens
 *
 */
public class AccountsService {

	private static Logger log = LoggerFactory.getLogger(AccountsService.class);
	
	private AccountsRepository accountsRepository;
	private TransactionsRepository transactionsRepository;
	private StatementsRepository statementsRepository;

	@Inject
	public AccountsService(AccountsRepository accountsRepository, TransactionsRepository transactionsRepository,
			StatementsRepository statementsRepository) {
		this.accountsRepository = accountsRepository;
		this.transactionsRepository = transactionsRepository;
		this.statementsRepository = statementsRepository;
	}

	/**
	 * Creates a new bank account.
	 * 
	 * P.S. uniqueness "is" insured by the account ID here.
	 * 
	 * TODO handle properly the transactional aspect in real world app
	 * 
	 * @param account
	 * @throws ServiceException
	 */
	public void createAccount(Account account) throws ServiceException {
		log.info("Create account {}", account);
		try {
			accountsRepository.add(account);
			statementsRepository.add(new Statement(account, 0.0, LocalDateTime.now()));
		} catch (DataAccessException e) {
			log.error("Error creating account {}", account, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * Performs a deposit to an account.
	 * 
	 * TODO handle properly the transactional aspect in real world app
	 * 
	 * @param account
	 * @param amount
	 * @throws ServiceException
	 */
	public void deposit(Account account, Double amount, LocalDateTime date) throws ServiceException {
		/*
		 * We suppose amount is positive.
		 * For the sake of simplicity here, we're not checking and throwing appropriate exception when it's not.
		 */
		log.info("Deposit {} for {} on {}", amount, account, date);
		try {
			transactionsRepository.add(new Transaction(account, amount, Operation.DEPOSIT, date));
			// adjust balance
			Statement statement = statementsRepository.get(account);
			// avoid weird double computations floating point surprises
			BigDecimal newBalance = BigDecimal.valueOf(statement.getBalance()).add(BigDecimal.valueOf(amount));
			statementsRepository.add(new Statement(account, newBalance.doubleValue(), LocalDateTime.now()));						
		} catch (DataAccessException e) {
			log.error("Error performing deposit  for account {}", account, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * Performs a withdrawal from an account.
	 * 
	 * TODO handle properly the transactional aspect in real world app
	 * 
	 * @param account
	 * @param amount
	 * @throws ServiceException
	 */
	public void withdraw(Account account, Double amount, LocalDateTime date) throws ServiceException {
		/*
		 * We suppose amount is positive.
		 * For the sake of simplicity, here we're not checking and throwing appropriate exception when it's not.
		 * 
		 * We suppose negative balance is allowed for the account.
		 * In real world app there would be really complex validation rules I guess.
		 */
		log.info("Withdraw {} for {} on {}", amount, account, date);
		try {
			transactionsRepository.add(new Transaction(account, amount, Operation.WITHDRAW, date));
			// adjust balance
			Statement statement = statementsRepository.get(account);
			BigDecimal newBalance = BigDecimal.valueOf(statement.getBalance()).subtract(BigDecimal.valueOf(amount));
			statementsRepository.add(new Statement(account, newBalance.doubleValue(), LocalDateTime.now()));						
		} catch (DataAccessException e) {
			log.error("Error performing withdrawal for account {}", account, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * Retrieves an account statement.
	 * 
	 * @param account
	 * @return
	 * @throws ServiceException
	 */
	public Statement getStatement(Account account) throws ServiceException {
		log.info("Statement for {}", account);
		try {
			return statementsRepository.get(account);
		} catch (DataAccessException e) {
			log.error("Error retrieving statement for account {}", account, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * Retrieves a "page" of an account transactions history, sorted by descending
	 * date order.
	 * 
	 * @param account
	 * @param offset
	 * @param size
	 * @return
	 * @throws ServiceException
	 */
	public List<Transaction> getTransactionsHistory(Account account, int offset, int size) throws ServiceException {
		try {
			return transactionsRepository.get(account, offset, size);
		} catch (DataAccessException e) {
			log.error("Error retrieving transactions history for account {}", account, e);
			throw new ServiceException(e);
		}
	}
	
	public String getBalanceAndTransactionsHistory(Account account, int size) throws ServiceException {
		try {
			List<Transaction> txs = transactionsRepository.get(account, 0, size);
			Statement statement = statementsRepository.get(account);
			
			StringBuilder sb = new StringBuilder();
			sb.append("\nBalance: ").append(statement.getBalance()).append(" on ").append(statement.getDate()).append("\n");
			sb.append("Operations:\n");
			for(Transaction tx: txs) {
				sb.append(rightPad(tx.getOperation().toString(), 10)).append(" | ").append(leftPad(tx.getAmount().toString(), 10)).append(" | ").append(tx.getDate()).append("\n");
			}
			return sb.toString();
		} catch (DataAccessException e) {
			log.error("Error establishing account status for {}", account, e);
			throw new ServiceException(e);
		}
	}
}
