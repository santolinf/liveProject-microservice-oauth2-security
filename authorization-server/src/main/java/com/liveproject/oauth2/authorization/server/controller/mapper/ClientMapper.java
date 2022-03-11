package com.liveproject.oauth2.authorization.server.controller.mapper;

import com.liveproject.oauth2.authorization.server.controller.dto.ClientDto;
import com.liveproject.oauth2.authorization.server.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Collection<ClientDto> clientsToClientDtos(Collection<Client> clients);

    @Mapping(target = "secret", ignore = true)
    ClientDto clientToClientDto(Client client);

    Client clientDtoToClient(ClientDto clientDto);
}
