package com.liveproject.oauth2.client.controller;

import com.liveproject.oauth2.client.controller.dto.HealthAdvice;
import com.liveproject.oauth2.client.proxy.HealthServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthAdviceController {

    private final HealthServiceProxy proxy;

    @PostMapping(value = "/advice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void postAdvice(@RequestBody List<HealthAdvice> healthAdvice) {
        proxy.callAdvice(healthAdvice);
    }
}
