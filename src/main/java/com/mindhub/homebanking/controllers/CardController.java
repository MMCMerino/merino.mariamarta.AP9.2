package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/cards")
    public List<CardDTO> getCards() {

        return cardService.getCards();
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Object> getCardById(@PathVariable Long id, Authentication authentication) {
        Card card = cardService.findById(id);
        Client client = clientService.findByEmail(authentication.getName());

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
        Client client = this.clientService.findByEmail(authentication.getName());
        return client.getCards().stream()
                .map(currentCard -> new CardDTO(currentCard))
                .collect(Collectors.toSet());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)

    public ResponseEntity<Object> newCard(@RequestParam CardType cardType,
                                          @RequestParam CardColor cardColor,
                                          Authentication authentication) {

        //debo hacer que el cliente logeado se le genere la tarjeta
        Client client = clientService.findByEmail(authentication.getName());
        Set<Card> cards = client.getCards();

        if(!cards.stream()
                .filter(card -> card.getType().equals(cardType))
                .filter(card -> card.getColor().equals(cardColor))
                .collect(Collectors.toSet()).isEmpty())
                 {
            return new ResponseEntity<>("You already have the "+cardColor+ " "+ cardType+ "card", HttpStatus.FORBIDDEN);

        } else{

            Card cardNew = new Card(client.getFirstName() + " " + client.getLastName()
                                        , cardType, cardColor
                                         , getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999) + "-" + getRandomNumber(0, 9999)
                , getRandomNumber(0, 999), LocalDate.now(), LocalDate.now().plusYears(5));
            client.addCard(cardNew);
            cardService.save(cardNew);

            return new ResponseEntity<>("Congratulations, you have a new "+cardColor+"card", HttpStatus.CREATED);

        }



        }


    public int getRandomNumber(int min, int max){
        return (int)((Math.random()*(max-min))+min);

}



}