package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/cards")
    public List<CardDTO> getCards() {

        return cardRepository.findAll()
                .stream()
                .map(currentCard -> new CardDTO(currentCard))
                .collect(Collectors.toList());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Object> getCardById(@PathVariable Long id, Authentication authentication) {
        Card card = cardRepository.findById(id).get();
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.BAD_GATEWAY);
        }

        if (card.getClient().equals(client)) {
            CardDTO cardDTO = new CardDTO(card);
            return new ResponseEntity<>(cardDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("No cars access", HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream()
                .map(currentCard -> new CardDTO(currentCard))
                .collect(Collectors.toSet());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)

    public ResponseEntity<Object> newCard(@RequestParam CardType cardType,
                                          @RequestParam CardColor cardColor,
                                          Authentication authentication) {

        //debo hacer que el cliente logeado se le genere la tarjeta
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        System.out.println(cards);
        if(!cards.stream()
                .filter(card -> card.getType().equals(cardType))
                .filter(card -> card.getColor().equals(cardColor))
                .collect(Collectors.toSet()).isEmpty())
                 {
            return new ResponseEntity<>("You already have the "+cardColor+ " "+ cardType+ "card", HttpStatus.FORBIDDEN);

        } if(cards.stream()
                .filter(card -> card.getType().equals(cardType))
                .filter(card -> card.getColor().equals(cardColor))
                .collect(Collectors.toSet()).isEmpty()) {

            Card cardNew = new Card(client.firstName + " " + client.lastName
                                        , cardType, cardColor
                                         , getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999)
                , getRandomNumber(0, 999), LocalDate.now(), LocalDate.now().plusYears(5));
            client.addCard(cardNew);
            cardRepository.save(cardNew);

            return new ResponseEntity<>("Congratulations, you have a new "+cardColor+"card", HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>("Error to created card", HttpStatus.NOT_ACCEPTABLE);
        }


        }


    public int getRandomNumber(int min, int max){
        return (int)((Math.random()*(max-min))+min);

}



}