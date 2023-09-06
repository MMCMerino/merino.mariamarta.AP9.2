package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getClientDTO() {
        return clientRepository.findAll()
                .stream()
                .map( currentClient -> new ClientDTO(currentClient) )
                .collect(Collectors.toList());
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }


    @Override
    public ClientDTO getClientDTO(Long id) {
        return new ClientDTO(this.findById(id));
    }

    @Override
    public ClientDTO getAuthenticatedClient(String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
    @Override
    public boolean existsByAccountNumber(long accountNumber) {
        return false; //Todo->verlo???
    }
}
