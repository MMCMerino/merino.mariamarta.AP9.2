package com.mindhub.homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    public Integer payments;
    public double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private  Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private  Loan loan;


    //Constructores


    public ClientLoan() {
    }

    public ClientLoan(Integer payments, double amount) {

        this.payments = payments;
        this.amount = amount;
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

    public Client getClient() {
        return client;
    }

    public Loan getLoan() {
        return loan;
    }



    //Setters


    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

}
