package com.mindhub.homebanking.models;


import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long  id;

    public String cardHolder;
    public  CardType type;
    public CardColor color;
    public String number;

    public Integer cvv;
    public LocalDate frontDate;

    public LocalDate thruDate;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private  Client client;
    //Constructores


    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, Integer cvv, LocalDate frontDate, LocalDate thruDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.frontDate = frontDate;
        this.thruDate = thruDate;
    }


    //Getters

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFrontDate() {
        return frontDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Client getClient() {
        return client;
    }
    //Setters

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setFrontDate(LocalDate frontDate) {
        this.frontDate = frontDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
