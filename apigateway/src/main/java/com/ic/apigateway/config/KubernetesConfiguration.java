package com.ic.apigateway.config;


import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("kubernetes")
@EnableDiscoveryClient
public class KubernetesConfiguration {
}
