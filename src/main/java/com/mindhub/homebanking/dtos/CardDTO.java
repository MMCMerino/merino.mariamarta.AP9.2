package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long  id;

    public String cardHolder;
    public CardType type;
    public CardColor color;
    public String number;

    public Integer cvv;
    public LocalDate frontDate;

    public LocalDate thruDate;

    //Constructores

    public CardDTO(Object currentCard ) {
    }
    public CardDTO (Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.frontDate = card.getFrontDate();
        this.thruDate = card.getThruDate();
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
}
