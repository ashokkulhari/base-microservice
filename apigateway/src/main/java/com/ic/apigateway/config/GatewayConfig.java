package com.ic.apigateway.config;

import com.ic.apigateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class named {@link GatewayConfig} for setting up API Gateway routes.
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Define the list of public endpoints
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/authentication/users/register",
            "/api/v1/authentication/users/login",
            "/api/v1/authentication/users/refresh-token",
            "/api/v1/authentication/users/logout"
    );

//    /**
//     * Configures the route locator to define the routing rules for the gateway.
//     *
//     * @param builder The RouteLocatorBuilder used to build the RouteLocator.
//     * @return A RouteLocator with the defined routes.
//     */
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("productservice", r -> r.path("/api/v1/products/**")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
//                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
//                        .uri("lb://productservice"))
//                .route("catalogservice", r -> r.path("/api/v1/catalogs/**")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
//                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
//                        .uri("lb://catalogservice"))
//                .route("authservice", r -> r.path("/api/v1/authentication/**")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
//                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
//                        .uri("lb://authservice"))
//                .route("userservice", r -> r.path("/api/v1/users/**")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
//                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
//                        .uri("lb://userservice"))
//                .build();
//    }


    @Bean
    public GatewayFilter globalJwtFilter() {
        JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
        config.setPublicEndpoints(PUBLIC_ENDPOINTS);
        return jwtAuthFilter.apply(config);
    }

}