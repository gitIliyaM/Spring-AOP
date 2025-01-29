package ru.t1.java.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDTO {

    @JsonProperty()
    private Long idClient;
    @JsonProperty()
    private Double amountTransaction;
    @JsonProperty()
    private String timeTransaction;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Double getAmountTransaction() {
        return amountTransaction;
    }

    public void setAmountTransaction(Double amountTransaction) {
        this.amountTransaction = amountTransaction;
    }

    public String getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(String timeTransaction) {
        this.timeTransaction = timeTransaction;
    }
}
