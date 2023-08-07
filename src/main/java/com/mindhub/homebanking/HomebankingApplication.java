package com.mindhub.homebanking;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,AccountRepository accountRepository){
		return (args) -> {

			Client client0 = new Client("Melba","Morel","melba@mindhub.com");
			clientRepository.save(client0);
			Client client1 = new Client("Maria","Martinez","mariamartinez@mindhub.com");
			clientRepository.save(client1);

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






		};
	}
}
