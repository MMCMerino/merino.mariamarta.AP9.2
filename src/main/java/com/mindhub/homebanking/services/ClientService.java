package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientDTO();
    Client findById(Long id);
    ClientDTO getClientDTO(Long id);

    ClientDTO getAuthenticatedClient(String email);

    Client findByEmail(String email);
    void save(Client client);
    boolean existsByAccountNumber(long accountNumber);
}
