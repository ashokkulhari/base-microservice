package com.ic.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/auth")
    public ResponseEntity<String> authFallback() {
        return new ResponseEntity<>("Auth service is unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}

