package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll()
                .stream().map( currentTransaction -> new TransactionDTO(currentTransaction))
                .collect(Collectors.toList());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Long id, Authentication authentication){
        Transaction transaction = transactionRepository.findById(id).get();
        Client client = clientRepository.findByEmail(authentication.getName());

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
        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();
        Account account1 = accountRepository.findByNumber(fromAccountNumber);
        Account account2 = accountRepository.findByNumber(toAccountNumber);

        if(fromAccountNumber.isBlank()
                || toAccountNumber.isBlank()|| description.isBlank()){
            return new ResponseEntity<>("Missing Data",HttpStatus.FORBIDDEN);
        } else if (account1== null || account2 == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }else if (client == null) {
            return new ResponseEntity<>("Client not logged in",HttpStatus.FORBIDDEN);

        }else if(account1.balance < amount){
            return new ResponseEntity<>("Amount not available",HttpStatus.FORBIDDEN);
        }else if(account1 == account2){
            return new ResponseEntity<>("The source account is the same as the destination account",HttpStatus.FORBIDDEN);
        }else{
            //todo generar la transaccion

            Transaction sourceTransaction = new Transaction(TransactionType.DEBIT,-amount, description,LocalDate.now());
            Transaction destinationTransaction = new Transaction(TransactionType.CREDIT,amount,description,LocalDate.now());
            account1.addTransaction(sourceTransaction);
            account2.addTransaction(destinationTransaction);
            account1.balance-=amount; // Preguntar si se puede hacer asi.
            account2.balance+=amount;
            transactionRepository.save(sourceTransaction);
            transactionRepository.save(destinationTransaction);



            return new ResponseEntity<>("Succesfull transaction",HttpStatus.CREATED);
        }

        }


    }


