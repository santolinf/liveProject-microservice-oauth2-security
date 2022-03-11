package com.liveproject.oauth2.authorization.server.controller;

import com.liveproject.oauth2.authorization.server.controller.dto.ClientDto;
import com.liveproject.oauth2.authorization.server.controller.mapper.ClientMapper;
import com.liveproject.oauth2.authorization.server.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientMapper mapper;
    private final ClientService clientService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addClient(@RequestBody ClientDto clientDto) {
        clientService.addClient(mapper.clientDtoToClient(clientDto));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ClientDto> getClients() {
        return mapper.clientsToClientDtos(clientService.getClients());
    }
}
