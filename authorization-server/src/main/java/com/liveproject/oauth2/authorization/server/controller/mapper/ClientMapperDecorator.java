package com.liveproject.oauth2.authorization.server.controller.mapper;

import com.liveproject.oauth2.authorization.server.controller.dto.ClientDto;
import com.liveproject.oauth2.authorization.server.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ClientMapperDecorator implements ClientMapper {

    @Autowired
    @Qualifier("delegate")
    private ClientMapper delegate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Client clientDtoToClient(ClientDto clientDto) {
        Client client = delegate.clientDtoToClient(clientDto);
        client.setSecret(passwordEncoder.encode(clientDto.getSecret()));

        return client;
    }
}
