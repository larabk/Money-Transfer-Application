package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/account/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Account getAccount(int id) {
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log((e.getMessage()));
        }
        return account;
    }

    public Account[] getAllAccounts() {
        Account[] accounts = null;
        try {
            ResponseEntity<Account[]> response =
                    restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public BigDecimal getBalance() {
        ResponseEntity<BigDecimal> response =
                restTemplate.exchange(API_BASE_URL + "balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
        return response.getBody();
    }


    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public long findAccountIdByUserId(long userId) {
        ResponseEntity<Long> response =
                restTemplate.exchange(API_BASE_URL + "/user/" + userId, HttpMethod.GET, makeAuthEntity(), Long.class);
        return response.getBody();
    }

    public String findUsernameByUserId(long userId) {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/" + userId, HttpMethod.GET, makeAuthEntity(), String.class);
        return response.getBody();
    }

    public long findUserIdByAccountId(int accountId) {
        ResponseEntity<Long> response =
                restTemplate.exchange(API_BASE_URL + accountId, HttpMethod.GET, makeAuthEntity(), Long.class);
        return response.getBody();
    }


}
