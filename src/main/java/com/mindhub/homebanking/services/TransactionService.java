package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
List<TransactionDTO> getTransaction();
Transaction findById(Long id);
void save(Transaction transaction);
}
