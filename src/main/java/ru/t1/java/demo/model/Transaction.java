package ru.t1.java.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idClient;

    @Column(nullable = false)
    private Double amountTransaction;

    @Column(nullable = false)
    private String timeTransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
