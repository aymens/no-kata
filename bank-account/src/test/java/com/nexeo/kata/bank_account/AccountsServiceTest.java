package com.nexeo.kata.bank_account;

import static com.nexeo.kata.bank_account.app.TestServicesHelper.accountsService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.stringContainsInOrder;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexeo.kata.bank_account.model.Account;
import com.nexeo.kata.bank_account.service.AccountsService;
import com.nexeo.kata.bank_account.service.exceptions.ServiceException;

public class AccountsServiceTest {

	private static Logger log = LoggerFactory.getLogger(AccountsServiceTest.class);

	private static AccountsService service;

	@BeforeClass
	public static void init() {
		service = accountsService();
	}

	@After
	public void cleanRepos() {

	}

	@Test
	public void depositMoney() throws ServiceException {
		// TODO in real word apps test data should be loaded otherwise, used to
		// do it with dbunit, and only test subject method would be executed
		// here.
		/*
		 * prepare test fixture
		 */
		Account account = new Account();
		service.createAccount(account);
		/*
		 * end data fixture preparation
		 */

		// test deposit
		service.deposit(account, 100.0, LocalDateTime.now());

		// TODO same for assertions, data changes should be checked by other
		// means than by calling other services that aren't the subject of this
		// test method.
		assertThat(service.getStatement(account).getBalance(), equalTo(100.0));
	}

	@Test
	public void withdrowMoney() throws ServiceException {
		// TODO in real word apps test data should be loaded otherwise, used to
		// do it with dbunit, and only test subject method would be executed
		// here.
		/*
		 * prepare test fixture
		 */
		Account account = new Account();
		service.createAccount(account);
		service.deposit(account, 100.0, LocalDateTime.now());
		/*
		 * end data fixture preparation
		 */
		// test withdrawal
		service.withdraw(account, 110.0, LocalDateTime.now());

		// TODO same for assertions, data changes should be checked by other
		// means than by calling other services that aren't the subject of this
		// test method.
		assertThat(service.getStatement(account).getBalance(), equalTo(-10.0));
	}

	@Test
	public void getStatment() throws ServiceException {
		// TODO in real word apps test data should be loaded otherwise, used to
		// do it with dbunit, and only test subject method would be executed
		// here.
		/*
		 * prepare test fixture
		 */
		Account account = new Account();
		service.createAccount(account);
		service.deposit(account, 100.0, LocalDateTime.now());
		// test withdrawal
		service.withdraw(account, 110.0, LocalDateTime.now());
		/*
		 * end data fixture preparation
		 */
		assertThat(service.getStatement(account).getBalance(), equalTo(-10.0));
	}

	@Test
	public void getTransactionsHistory() throws ServiceException {
		// TODO in real word apps test data should be loaded otherwise, used to
		// do it with dbunit, and only test subject method would be executed
		// here.
		/*
		 * prepare test fixture
		 */
		Account account = new Account();
		service.createAccount(account);
		service.deposit(account, 100.0, LocalDateTime.now());
		// test withdrawal
		service.withdraw(account, 110.0, LocalDateTime.now());
		/*
		 * end data fixture preparation
		 */
		assertThat(service.getTransactionsHistory(account, 0, 10), hasSize(2));
	}

	@Test
	public void getGetBalanceAndTransactionsHistory() throws ServiceException {
		// TODO in real word apps test data should be loaded otherwise, used to
		// do it with dbunit, and only test subject method would be executed
		// here.
		/*
		 * prepare test fixture
		 */
		Account account = new Account();
		service.createAccount(account);
		service.deposit(account, 100.0, LocalDateTime.now());
		// test withdrawal
		service.withdraw(account, 110.0, LocalDateTime.now());
		/*
		 * end data fixture preparation
		 */
		log.info(service.getBalanceAndTransactionsHistory(account, 10));
		assertThat(service.getBalanceAndTransactionsHistory(account, 10),
				stringContainsInOrder("Balance: -10.0", "WITHDRAW | 110.0", "DEPOSIT | 100.0"));
	}

	@AfterClass
	public static void tearDown() {
		// NOOP
	}
}
