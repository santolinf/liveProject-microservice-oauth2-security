package com.liveproject.oauth2.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Value("${liveproject.oauth2.authorization.health.resources.url}")
    private String healthResourcesBaseURL;

    @Bean
    public RouteLocator healthRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/profile/**")
                        .uri(healthResourcesBaseURL + "/profile")
                )
                .route(p -> p
                        .path("/metric/**")
                        .uri(healthResourcesBaseURL + "/metric")
                )
                .route(p -> p
                        .path("/advice/**")
                        .uri(healthResourcesBaseURL + "/advice")
                )
                .build();
    }
}
