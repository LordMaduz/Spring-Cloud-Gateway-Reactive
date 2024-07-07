package com.spring.cloud.config;

import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
@Configuration
public class ApplicationJwtConfig {

    private RSAPublicKey publicKey;

}