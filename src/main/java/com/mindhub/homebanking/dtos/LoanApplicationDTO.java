package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.LoanApplication;

public class LoanApplicationDTO {
    private Long id;
    private Long loanId;
    private double amount;
    private int payments;

    private String toAccountNumber;



//Constructores

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(LoanApplication loanApplication){
        this.id = loanApplication.getId();
        this.loanId = loanApplication.getLoans().getId();
        this.amount = loanApplication.getAmount();
        this.payments = loanApplication.getPayments();
        this.toAccountNumber = loanApplication.getToAccountNumber();

    }

    //Getters


    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
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
}
