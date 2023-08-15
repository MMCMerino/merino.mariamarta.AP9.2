package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    public String firstName;
    public String lastName;

    public String email;

    private Set<AccountDTO> accounts = new HashSet<>();

    private Set<ClientLoanDTO> loans = new HashSet<>();

    private Set<CardDTO> cards = new HashSet<>();




    //Constructor

    public ClientDTO() {
    }

    public ClientDTO(Client client) {

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                                .stream()
                                .map(currentAccount -> new AccountDTO(currentAccount))
                                .collect(Collectors.toSet());

        this.loans = client.getClientLoans()
                                .stream()
                                .map(currentClientLoan -> new ClientLoanDTO(currentClientLoan))
                                .collect(Collectors.toSet());
        this.cards = client.getCards()
                                 .stream()
                                 .map(currentCard -> new CardDTO(currentCard))
                                 .collect(Collectors.toSet());


    }
    //Getters
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
