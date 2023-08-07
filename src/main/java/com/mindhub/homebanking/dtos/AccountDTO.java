package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class AccountDTO {

    private Long id;
    public String number;
    public double balance;
    public LocalDate creationDate;

    //Constructor

    public AccountDTO() {
    }
    public AccountDTO(Account account) {

        this.id = account.getId();
        this.number = account.getNumber();
        this.balance= account.getBalance();
        this.creationDate = account.getCreationDate();

    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}



