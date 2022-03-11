package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.exeption.ClientAlreadyExistsException;
import com.liveproject.oauth2.authorization.server.model.Client;
import com.liveproject.oauth2.authorization.server.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Add a new client to the database
     */
    public void addClient(Client client) {
        // check that the client id does not already exist
        if (clientRepository.existsByClientId(client.getClientId())) {
            throw new ClientAlreadyExistsException(client.getClientId());
        }

        if (Objects.nonNull(client.getGrantTypes())) {
            client.getGrantTypes().forEach(grant -> grant.setClient(client));
        }
        clientRepository.save(client);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }
}
