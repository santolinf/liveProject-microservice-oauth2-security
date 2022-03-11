package com.liveproject.oauth2.authorization.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private long id;
    private String clientId;
    private String secret;
    private String scope;
    private String redirectUri;

    private List<GrantTypeDto> grantTypes;
}
