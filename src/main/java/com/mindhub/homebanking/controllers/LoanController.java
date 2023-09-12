package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Object> getLoanByName(@PathVariable String name,
                                              Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Loan loan = loanService.findByName(name);

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.BAD_GATEWAY);
        }

        if (loan.getClientLoans().equals(client)) {
            LoanDTO loanDTO = new LoanDTO(loan);

            return new ResponseEntity<>(loanDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("No loans access", HttpStatus.NOT_ACCEPTABLE);

        }

    }

    @Transactional
    @PostMapping("/loans")

    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                          Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account toAccount = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());
        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());





       if(loanApplicationDTO.getPayments() == 0 ||loanApplicationDTO.getToAccountNumber().isBlank() ||loanApplicationDTO.getAmount()<=10000){
            return new ResponseEntity<>("Invalid parameters",HttpStatus.FORBIDDEN);

        } else if(loan.equals(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("The requested loan does not exist",HttpStatus.FORBIDDEN);

        }else if(loanApplicationDTO.getAmount() > loan.getMaxAmount() ){
            return new ResponseEntity<>("Maximum amount exceeded",HttpStatus.FORBIDDEN);
        }else if(! loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Choose another financing plan",HttpStatus.FORBIDDEN);

        }else if (!(client.getAccounts().contains(toAccount))) {
           return new ResponseEntity<>("Account error", HttpStatus.FORBIDDEN);
       }/*else if(!client.getClientLoans().stream().
               filter(clientLoans ->clientLoans.getLoan().equals(loanApplicationDTO.getLoanId()))
               .collect(Collectors.toSet()).isEmpty()){//todo-> verificar que el prestamo no este tomado
           return new ResponseEntity<>("You already acquired that loan",HttpStatus.FORBIDDEN);
        }*/else{
            //Creo el nuevo prestamo

            ClientLoan clientLoanNew = new ClientLoan(loanApplicationDTO.getPayments(),(loanApplicationDTO.getAmount()+loanApplicationDTO.getAmount()*0.2));
            client.addClientLoan(clientLoanNew);
            loan.addClientLoanLoan(clientLoanNew);
            clientLoanService.save(clientLoanNew);

            //Agrego la transaccion y modifico los balances

             Transaction transactionNew = new Transaction(TransactionType.CREDIT,
                     loanApplicationDTO.getAmount(),"Obtaining a loan ",LocalDate.now());
            toAccount.addTransaction(transactionNew);
            toAccount.setBalance(toAccount.getBalance()+loanApplicationDTO.getAmount());// todo -> ver donde va el +20%
            transactionService.save(transactionNew);
            accountService.save(toAccount);

            return new ResponseEntity<>("Exit",HttpStatus.CREATED);

        }



    }


}