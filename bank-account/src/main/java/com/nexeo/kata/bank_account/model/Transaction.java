package com.nexeo.kata.bank_account.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.nexeo.kata.bank_account.repo.enums.Operation;

public class Transaction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private Double amount;
	private Operation operation;
	private LocalDateTime date;
	
	@SuppressWarnings("unused")
	private Transaction() {
	}

	public Transaction(Account account, Double amount, Operation operation, LocalDateTime date) {
		super();
		this.account = account;
		this.amount = amount;
		this.operation = operation;
		this.date = date;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Transaction [account=" + account + ", amount=" + amount + ", operation=" + operation + ", date=" + date
				+ "]";
	}
}
