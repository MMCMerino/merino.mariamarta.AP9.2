package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        List<Account> allAccounts = accountRepository.findAll();
        List<AccountDTO> convertedListAccount = allAccounts
                                                        .stream()
                                                        .map(currentAccount -> new AccountDTO(currentAccount))
                                                        .collect(Collectors.toList());
        return convertedListAccount;

    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        Optional<Account> accountOptional =  accountRepository.findById(id);
        return new AccountDTO(accountOptional.get());

    }
}
