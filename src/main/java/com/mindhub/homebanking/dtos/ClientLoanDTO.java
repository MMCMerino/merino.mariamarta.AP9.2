package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;

    private String name;
    public Integer payments;
    public double amount;

    //Constructores


    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
    }

    //Getters

    public Long getId() {
        return id;
    }

    public Integer getPayments() {
        return payments;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
//Setters

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
