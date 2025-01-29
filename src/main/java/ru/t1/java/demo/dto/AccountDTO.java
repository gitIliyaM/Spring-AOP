package ru.t1.java.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO {

    @JsonProperty()
    private Long idClient;
    @JsonProperty()
    private String accountType;
    @JsonProperty()
    private Double balance;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
