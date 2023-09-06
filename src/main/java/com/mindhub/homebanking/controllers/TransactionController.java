package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransaction();
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Long id, Authentication authentication){
        Transaction transaction = transactionService.findById(id);
        Client client = clientService.findByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.BAD_GATEWAY);
        }

        if (transaction.getAccount().getClient().equals(client)) {
            TransactionDTO transactionDTO = new TransactionDTO(transaction);

            return new ResponseEntity<>(transactionDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("No transaction access", HttpStatus.NOT_ACCEPTABLE);

        }

    }

    @RequestMapping(path = "/transactions", method = RequestMethod.POST)

    public ResponseEntity<Object> newTransaction(@RequestParam String fromAccountNumber,
                                          @RequestParam String toAccountNumber,
                                          @RequestParam double amount,
                                          @RequestParam String description,
                                          Authentication authentication) {

        //debo hacer que el cliente logeado pueda generar la transaccion
        Client client = clientService.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();

        Account fromAccount = accountService.findByNumber(fromAccountNumber);
        Account toAccount = accountService.findByNumber(toAccountNumber);

        if(fromAccountNumber.isBlank()
                || toAccountNumber.isBlank()|| description.isBlank()){
            return new ResponseEntity<>("Missing Data",HttpStatus.FORBIDDEN);
        } else if (fromAccount== null || toAccount == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }else if (client == null) {
            return new ResponseEntity<>("Client not logged in",HttpStatus.FORBIDDEN);

        }else if(!(client.getAccounts().contains(fromAccount))){

            return new ResponseEntity<>("The account is not yours",HttpStatus.FORBIDDEN);
        }else if (amount<=0) {
            return new ResponseEntity<>("Amount not allowed",HttpStatus.FORBIDDEN);
        }
        else if(fromAccount.getBalance() < amount){
            return new ResponseEntity<>("Amount not available",HttpStatus.FORBIDDEN);
        }else if(fromAccount == toAccount){
            return new ResponseEntity<>("The source account is the same as the destination account",HttpStatus.FORBIDDEN);
        }else{
            //generar la transaccion

            Transaction sourceTransaction = new Transaction(TransactionType.DEBIT,-amount, description,LocalDate.now());
            Transaction destinationTransaction = new Transaction(TransactionType.CREDIT,amount,description,LocalDate.now());
            fromAccount.addTransaction(sourceTransaction);
            toAccount.addTransaction(destinationTransaction);
            fromAccount.setBalance(fromAccount.getBalance()-amount);
            toAccount.setBalance(toAccount.getBalance()+amount);
            transactionService.save(sourceTransaction);
            transactionService.save(destinationTransaction);
            accountService.save(fromAccount);
            accountService.save(toAccount);



            return new ResponseEntity<>("Succesfull transaction",HttpStatus.CREATED);
        }

        }


    }


