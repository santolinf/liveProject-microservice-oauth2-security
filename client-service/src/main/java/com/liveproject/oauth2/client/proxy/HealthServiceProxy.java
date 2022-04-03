package com.liveproject.oauth2.client.proxy;

import com.liveproject.oauth2.client.controller.dto.HealthAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HealthServiceProxy {

    private static final String AUTHORIZATION = "Authorization";

    @Value("${health.service.url}")
    private String healthServiceURL;

    private final TokenManager tokenManager;
    private final RestTemplate restTemplate;

    public void callAdvice(List<HealthAdvice> healthAdvice) {
        String token = tokenManager.getAccessToken();

        String url = healthServiceURL + "/advice";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Bearer " + token);
        HttpEntity<List> request = new HttpEntity<>(healthAdvice, headers);

        restTemplate.postForObject(
                url,
                request,
                Void.class
        );
    }
}
