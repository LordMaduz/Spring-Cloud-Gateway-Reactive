package com.spring.cloud.handler;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OAuth2BearerTokenHandler {

    public Mono<Map<String, Object>> getClaims() {
        return ReactiveSecurityContextHolder.getContext()
            .map(context -> context.getAuthentication()
                .getPrincipal())
            .map(authentication -> (Jwt) authentication)
            .map(Jwt::getClaims);
    }

    public Mono<Collection<String>> getAuthorities() {
        return ReactiveSecurityContextHolder.getContext()
            .map(context -> context.getAuthentication()
                .getAuthorities())
            .map(grantedAuthorities -> grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
    }
}
