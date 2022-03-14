package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.model.ApplicationClient;
import com.liveproject.oauth2.authorization.server.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientRepository.findClientByClientId(clientId)
                .map(ApplicationClient::new)
                .orElseThrow(() -> new ClientRegistrationException(clientId));
    }
}
