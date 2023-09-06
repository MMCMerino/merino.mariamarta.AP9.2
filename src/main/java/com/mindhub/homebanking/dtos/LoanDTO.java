package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import javax.persistence.ElementCollection;
import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private double maxAmount;

    @ElementCollection
    public List<Integer> payments;


    //Constructores

    public LoanDTO() {
    }

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();

    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
