package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){

        return  accountRepository.findAll()
                .stream()
                .map(currentAccount -> new AccountDTO(currentAccount))
                .collect(Collectors.toList());

    }


    @GetMapping("/accounts/{id}")
   /* public AccountDTO getAccountById(@PathVariable Long id){   //public ResponseEntity<Object>

        return new AccountDTO(accountRepository.findById(id).get());

    }*/


    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication) {
        Account account = accountRepository.findById(id).get();
        Client client = clientRepository.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("Cliente not found", HttpStatus.BAD_GATEWAY);
        }

        if(account.getClient().equals(client)){
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>("No account access", HttpStatus.NOT_ACCEPTABLE);

        }
    }


}
