package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {




    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){

        return  accountService.getAccounts();

    }



   @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication) {
        Account account = accountService.findById(id);
        Client client = clientService.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.BAD_GATEWAY);
        }

        if(account.getClient().equals(client)){
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>("No account access", HttpStatus.NOT_ACCEPTABLE);

        }
    }

    //Post  y Get para la creacion de una nueva cuenta y asociacion al cliente logeado
    //ver lo de autentiado -> Funciona

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO>  getAccounts1(Authentication authentication){
       Client client = this.clientService.findByEmail(authentication.getName());
       return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }



    @PostMapping("/clients/current/accounts")

    public ResponseEntity<Object> newAccount(Authentication authentication){

        //debo hacer que el cliente logeado se le genere la cuenta
       Client client = this.clientService.findByEmail(authentication.getName());
       long accountNumber = Math.round(Math.random()*100000000);//genero un numero aleatorio de 8 digitos
        do {
            //cantidad de cuentas es el numero de elementos de la tabla de la base de datos accounts
            if (client.getAccounts().size() < 3) {    //si tiene menos de tres cuentas procedo a crear cuenta

                Account accountNew = new Account("VIN" + accountNumber, 0, LocalDate.now());
                client.addAccount(accountNew);
                accountService.save(accountNew);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("You already have the maximum number of accounts", HttpStatus.FORBIDDEN);
            }
        }while(clientService.existsByAccountNumber(accountNumber));//crea la cuenta mientras no exista ese numero de cuenta
    }

}
