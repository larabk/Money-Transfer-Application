package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Account {

    @NotBlank(message = "accountId cannot be blank")
    private int accountId;
    @NotBlank(message = "userId cannot be blank")
    private int userId;
    private BigDecimal balance;

    @NotBlank(message = "username cannot be blank")

    public Account(int accountId, int userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Account() {

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
