package com.nexeo.kata.bank_account.model;

public class Account extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName, lastName;
	/* TODO
	 * Intended for more realistic implementation of Kata with real Jpa persistence in an embedded DB.
	 * For the sake of saving time, real persistence has been abandoned for the time being so has been these properties.
	 */
//	private Statement statement;
//	private List<Transaction> transactions = null;

	public Account() {
	}
	
	public Account(Integer id) {
		super(id);
	}

	public Account(Integer id, String firstName, String lastName) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//	public Statement getStatement() {
//		return statement;
//	}
//
//	public void setStatement(Statement statement) {
//		this.statement = statement;
//	}

//	public List<Transaction> getTransactions() {
//		return transactions;
//	}
//
//	public void setTransactions(List<Transaction> transactions) {
//		this.transactions = transactions;
//	}
}
