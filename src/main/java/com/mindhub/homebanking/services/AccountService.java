package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();

    Account findById(Long id);
    void getAccountById(Long id,String email);

    void save(Account account);

    Account findByNumber(String number);

}
