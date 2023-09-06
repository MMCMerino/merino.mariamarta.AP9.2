package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private double balance;
    private LocalDate creationDate;
    private Set<TransactionDTO> transactions = new HashSet<>();


    //Constructor

    public AccountDTO() {
    }
    public AccountDTO(Account account) {

        this.id = account.getId();
        this.number = account.getNumber();
        this.balance= account.getBalance();
        this.creationDate = account.getCreationDate();
        this.transactions = account.getTransactions()
                .stream()
                .map(currentTransaction -> new TransactionDTO(currentTransaction))
                .collect(Collectors.toSet());


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

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}



