package com.ic.apigateway.filter;

import com.ic.apigateway.client.UserServiceClient;
import com.ic.apigateway.config.TokenConfigurationParameter;
import com.ic.apigateway.model.Token;
import feign.FeignException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.List;

/**
 * A custom Gateway filter named {@link JwtAuthenticationFilter} that handles JWT authentication for requests.
 * This filter validates JWT tokens for all requests except those to public endpoints.
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    /**
     * Configuration class for JwtAuthenticationFilter.
     * It holds a list of public endpoints that should not be filtered.
     */
    public static class Config {
        // List of public endpoints that should not be filtered
        private List<String> publicEndpoints;

        /**
         * Gets the list of public endpoints.
         *
         * @return the list of public endpoints
         */
        public List<String> getPublicEndpoints() {
            return publicEndpoints;
        }

        /**
         * Sets the list of public endpoints.
         *
         * @param publicEndpoints the list of public endpoints to set
         * @return the updated Config object
         */
        public Config setPublicEndpoints(List<String> publicEndpoints) {
            this.publicEndpoints = publicEndpoints;
            return this;
        }

    }

  @Autowired
  private TokenConfigurationParameter tokenConfigurationParameter;

//    private UserDetails getCachedUserDetails(String username) {
//        Cache cache = cacheManager.getCache("userDetailsCache");
//        return cache != null ? cache.get(username, UserDetails.class) : null;
//    }
//
//    private void cacheUserDetails(String username, UserDetails userDetails) {
//        Cache cache = cacheManager.getCache("userDetailsCache");
//        if (cache != null) {h
//            cache.put(username, userDetails);
//        }
//    }
//
//    private UserDetails fetchUserDetailsFromAuthService(String token) {
//        // Call the auth-service to get user details if not in cache
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<UserDetails> response = restTemplate.exchange(
//                "http://AUTH-SERVICE/api/auth/me", HttpMethod.GET, entity, UserDetails.class);
//
//        return response.getBody();
//    }
    public void verifyAndValidate(final String jwt) {

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(tokenConfigurationParameter.getPublicKey())
                    .build()
                    .parseSignedClaims(jwt);

            // Log the claims for debugging purposes
            Claims claims = claimsJws.getPayload();
            log.info("Token claims: {}", claims);

            // Additional checks (e.g., expiration, issuer, etc.)
            if (claims.getExpiration().before(new Date())) {
                throw new JwtException("Token has expired");
            }

            log.info("Token is valid");

        } catch (ExpiredJwtException e) {
            log.error("Token has expired", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired", e);
        } catch (JwtException e) {
            log.error("Invalid JWT token", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token", e);
        } catch (Exception e) {
            log.error("Error validating token", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error validating token", e);
        }
    }

    /**
     * Applies the JWT authentication filter to the gateway.
     *
     * @param config the configuration for the filter
     * @return the gateway filter
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // Skip filtering for public endpoints
            if (config != null && config.getPublicEndpoints().stream().anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (Token.isBearerToken(authorizationHeader)) {
                String jwt = Token.getJwt(authorizationHeader);

                // Inject UserServiceClient here
                ApplicationContext context = exchange.getApplicationContext();
                UserServiceClient userServiceClient = context.getBean(UserServiceClient.class);

                return Mono.fromCallable(() -> {
                            verifyAndValidate(jwt);
                            log.debug("Token validation succeeded for path: {}", path);
                            return true;
                        })
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(valid -> chain.filter(exchange))
                        .onErrorResume(e -> {
                            log.error("Token validation failed for path: {}", path, e);
                            if (e instanceof FeignException.Unauthorized || e instanceof FeignException.Forbidden) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            return exchange.getResponse().setComplete();
                        });
            }
            log.warn("Missing or invalid Authorization header for path: {}", path);
            return chain.filter(exchange);
        };
    }
//try {
//        // 1. Validate the JWT token using a synchronous (non-reactive) service call
//        userServiceClient.validateToken(jwt);
//        log.debug("Token validation succeeded for path: {}", path);
//
//        // 2. If the token is valid, continue with the filter chain (routing the request downstream)
//        chain.doFilter(request, response);
//
//    } catch (FeignException e) {
//        // 3. Handle any Feign client errors (synchronous version)
//        log.error("Token validation failed for path: {}", path, e);
//
//        if (e instanceof FeignException.Unauthorized || e instanceof FeignException.Forbidden) {
//            // 4. If the token is invalid or forbidden, return 401 Unauthorized
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            // 5. For any other exceptions, return 500 Internal Server Error
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//
//        // 6. End the request processing by returning the response
//        return;
//    }

}