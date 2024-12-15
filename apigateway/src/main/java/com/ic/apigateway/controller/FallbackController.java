package com.ic.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FallbackController {

    @GetMapping("/fallback/auth")
    public ResponseEntity<String> authFallback() {
        log.debug("Gateway Controller :authFallback");
        return new ResponseEntity<>("Auth service is unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
    @GetMapping("/fallback/user")
    public ResponseEntity<String> userFallback() {
        log.debug("Gateway Controller :userFallback");
        return new ResponseEntity<>("User service is unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/product")
    public ResponseEntity<String> productFallback() {
        log.debug("Gateway Controller :productFallback");
        return new ResponseEntity<>("Product service is unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/fallback/catalog")
    public ResponseEntity<String> catalogFallback() {
        log.debug("Gateway Controller :catalogFallback");
        return new ResponseEntity<>("Catalog service is unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}

