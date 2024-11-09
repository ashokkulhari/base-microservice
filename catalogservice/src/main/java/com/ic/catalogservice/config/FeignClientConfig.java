package com.ic.catalogservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ic.catalogservice.model.common.dto.response.CustomResponse;
import com.ic.catalogservice.serializer.CustomResponseMixin;
import com.ic.catalogservice.serializer.LocalDateTimeSerializer;
import com.ic.catalogservice.serializer.UsernamePasswordAuthenticationTokenMixin;
import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Configuration class named {@link FeignClientConfig} for setting up Feign client components.
 * Configures custom error handling, object mapping, and decoding for Feign clients.
 */
@Slf4j
@Configuration
public class FeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    /**
     * Provides a custom {@link ObjectMapper} bean configured with Jackson modules.
     *
     * @return a configured {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(UsernamePasswordAuthenticationToken.class, UsernamePasswordAuthenticationTokenMixin.class);
        objectMapper.addMixIn(CustomResponse.class, CustomResponseMixin.class);
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for date/time support

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        objectMapper.registerModule(module);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown properties
        return objectMapper;
    }

    /**
     * Provides a custom {@link Decoder} bean for Feign clients.
     *
     * @param objectMapper the {@link ObjectMapper} to use for decoding
     * @return a {@link Decoder} instance
     */
    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return new CustomDecoder(objectMapper);
    }

    /**
     * Provides a custom {@link ErrorDecoder} bean for Feign clients.
     *
     * @return a {@link ErrorDecoder} instance
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    private static class CustomDecoder implements Decoder {

        private final ObjectMapper objectMapper;

        public CustomDecoder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException {
            // Handle specific HTTP status codes and throw corresponding FeignExceptions
            if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
                throw new FeignException.Unauthorized("Unauthorized", response.request(), response.request().body(), response.headers());
            }
            if (response.status() == HttpStatus.FORBIDDEN.value()) {
                throw new FeignException.Forbidden("Forbidden", response.request(), response.request().body(), response.headers());
            }
            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                throw new FeignException.NotFound("Not Found", response.request(), response.request().body(), response.headers());
            }
            if (response.status() == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                throw new FeignException.MethodNotAllowed("Method Not Allowed", response.request(), response.request().body(), response.headers());
            }
            if (response.status() == HttpStatus.BAD_REQUEST.value()) {
                throw new FeignException.BadRequest("Bad Request", response.request(), response.request().body(), response.headers());
            }

            // Deserialize the response body using Jackson
            if (response.body() != null) {
                InputStream inputStream = response.body().asInputStream();
                return objectMapper.readValue(inputStream, objectMapper.constructType(type));
            }

            return null;
        }
    }

    private static class CustomErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            HttpStatus status = HttpStatus.valueOf(response.status());
            // Handle specific HTTP status codes and return corresponding FeignExceptions
            if (status == HttpStatus.UNAUTHORIZED) {
                return new FeignException.Unauthorized("Unauthorized", response.request(), response.request().body(), response.headers());
            }
            if (status == HttpStatus.FORBIDDEN) {
                return new FeignException.Forbidden("Forbidden", response.request(), response.request().body(), response.headers());
            }
            if (status == HttpStatus.NOT_FOUND) {
                return new FeignException.NotFound("Not Found", response.request(), response.request().body(), response.headers());
            }
            if (status == HttpStatus.METHOD_NOT_ALLOWED) {
                return new FeignException.MethodNotAllowed("Method Not Allowed", response.request(), response.request().body(), response.headers());
            }
                return new FeignException.BadRequest("Bad Request", response.request(), response.request().body(), response.headers());

        }
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String token = getToken();
                if (token != null) {
                    requestTemplate.header("Authorization",  token);
                }
            }

            private String getToken() {

                Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
                System.out.println("getToken authentication "+authentication);
//                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (authentication instanceof UsernamePasswordAuthenticationToken) {

                    Object principal=authentication.getPrincipal();
                    System.out.println("principal "+principal);
                    if (principal instanceof Jwt) {
                        String token= ((Jwt) principal).getTokenValue();
                        System.out.println("token :"+((Jwt) principal).getTokenValue());
                        return "Bearer "+token;
                    }

                }
//                return null;



//                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                return requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                System.out.println("requestAttributes "+requestAttributes);
                if (requestAttributes != null) {
                    return  (String) requestAttributes.getRequest().getAttribute(HttpHeaders.AUTHORIZATION);
                }
                return null;
            }
        };
    }
}