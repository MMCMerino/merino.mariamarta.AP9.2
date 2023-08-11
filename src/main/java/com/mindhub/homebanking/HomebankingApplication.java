package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return (args) -> {
			//Creo dos clientes
			Client client0 = new Client("Melba","Morel","melba@mindhub.com");
			clientRepository.save(client0);
			Client client1 = new Client("Maria","Martinez","mariamartinez@mindhub.com");
			clientRepository.save(client1);

			//Creo cuentas para los clientes
			Account account0 = new Account("VIN001",5000, LocalDate.now());
			client0.addAccount(account0);
			accountRepository.save(account0);


			Account account1 = new Account("VIN002",7500, LocalDate.now().plusDays(1));
			client0.addAccount(account1);
			accountRepository.save(account1);


			Account account2 = new Account("VIN003",5500, LocalDate.now());
			client1.addAccount(account2);
			accountRepository.save(account2);


			Account account3 = new Account("VIN004",9000, LocalDate.now().plusDays(1));
			client1.addAccount(account3);
			accountRepository.save(account3);

			//Creo las transacciones de cada cuenta
            //Transactions VIN001
			Transaction transaction0 = new Transaction(TransactionType.CREDIT,  500000, "Payment month august", LocalDate.now());
			account0.addTransaction(transaction0);
			transactionRepository.save(transaction0);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT,  -15000, "Auto insurance payment", LocalDate.now());
			account0.addTransaction(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.DEBIT,  -5000, "Supermarket", LocalDate.now());
			account0.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			//Transactions VIN002

			Transaction transaction3 = new Transaction(TransactionType.CREDIT,  50000, "Collect rent", LocalDate.now());
			account1.addTransaction(transaction3);
			transactionRepository.save(transaction3);

			//Transactions VIN003

			Transaction transaction4 = new Transaction(TransactionType.CREDIT,  700000, "Payment month august", LocalDate.now());
			account2.addTransaction(transaction4);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(TransactionType.DEBIT,  -17000, "Auto insurance payment", LocalDate.now());
			account2.addTransaction(transaction5);
			transactionRepository.save(transaction5);

			Transaction transaction6 = new Transaction(TransactionType.DEBIT,  -6000, "Butcher shop", LocalDate.now());
			account2.addTransaction(transaction6);
			transactionRepository.save(transaction6);

			//Transaction VIN004

			Transaction transaction7 = new Transaction(TransactionType.CREDIT,  50000, "Payment arrangement", LocalDate.now());
			account3.addTransaction(transaction7);
			transactionRepository.save(transaction7);

			//Creo los distintos tipos de prestamos: hipotecario, personal y automotriz

			Loan loan0 = new Loan("Mortgage",500000, List.of(12, 24, 36, 48, 60));
			loanRepository.save(loan0); // guardo el prestamo hipotecario en el repositorio

			Loan loan1 = new Loan("Personal", 100000, List.of(6,12,24));
			loanRepository.save(loan1);

			Loan loan2 = new Loan("Car",300000,List.of(6,12,24,36));
			loanRepository.save(loan2);


			//Creo los prestamos para los distintos clientes
			//Prestamos de Melba
			ClientLoan clientLoan0 = new ClientLoan(60,400000);
			client0.addClientLoan(clientLoan0); //le asigno al cliente el prestamos
			loan0.addClientLoanLoan(clientLoan0); //Le digo que tipo de prestamo es
			clientLoanRepository.save(clientLoan0);

			ClientLoan clientLoan1 = new ClientLoan(12,50000);
			client0.addClientLoan(clientLoan1);
			loan1.addClientLoanLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);

			//Prestamos de Maria client1

			ClientLoan clientLoan2 = new ClientLoan(24,100000);
			client1.addClientLoan(clientLoan2);
			loan1.addClientLoanLoan(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(36,200000);
			client1.addClientLoan(clientLoan3);
			loan2.addClientLoanLoan(clientLoan3);


		};
	}
}
