package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface AccountDao {

    List<Account> getAllAccounts();

    int findAccountIdByUserId (int userId);

    int findUserIdByAccountId(int accountId);

    Account findAccountByUserId (int userId);

    String getUsernameByAccountId (int accountId);

    Account create(Account account);

    void addBalance(Transfer transfer);

    void subtractBalance(Transfer transfer);
}
