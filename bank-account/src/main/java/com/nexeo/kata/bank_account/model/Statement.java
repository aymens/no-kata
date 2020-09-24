package com.nexeo.kata.bank_account.model;

import java.time.LocalDateTime;

public class Statement extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private Double balance;
	private LocalDateTime date;

	public Statement(Account account, Double balance, LocalDateTime date) {
		this.account = account;
		this.balance = balance;
		this.date = date;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Statement [account=" + account + ", balance=" + balance + ", date=" + date + "]";
	}
}
