package com.microservices.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/email-microservice/**")
                        .filters(f -> f.rewritePath("/email-microservice/(?<segment>.*)", "/${segment}"))
                        .uri("lb://EMAIL-MICROSERVICE"))
                .build();
    }
}
