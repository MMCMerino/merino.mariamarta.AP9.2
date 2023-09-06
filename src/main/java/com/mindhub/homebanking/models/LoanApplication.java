package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;

    private double amount;
    private int payments;

    private String toAccountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private  Loan loan;
    //Constructores

    public LoanApplication() {
    }

    public LoanApplication( double amount, int payments, String toAccountNumber) {
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;

    }

    //Getters Y Setters

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }



    public Loan getLoans() {
        return loan;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setToAccountNumber(String toAccount) {
        this.toAccountNumber = toAccountNumber;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
