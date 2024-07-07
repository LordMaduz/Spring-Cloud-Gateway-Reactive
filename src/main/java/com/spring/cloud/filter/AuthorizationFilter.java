package com.spring.cloud.filter;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.spring.cloud.handler.CedarAccessValidationHandler;
import com.spring.cloud.handler.OAuth2BearerTokenHandler;
import com.spring.cloud.model.ValidationAccessHeaders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements WebFilter {

    private final OAuth2BearerTokenHandler bearerTokenHandler;
    private final CedarAccessValidationHandler accessValidationHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final Mono<Map<String, Object>> claimsMono = bearerTokenHandler.getClaims();
        return claimsMono.flatMap(claims -> accessValidationHandler
            .fetchAuthorizationResponse("PRODUCT_SERVICE",
                claims.get(ValidationAccessHeaders.SECURITY_GROUP.getType()).toString(),
                null,
                null,
                null)
            .flatMap(response -> {
                if (Boolean.FALSE.equals(response.getResult()
                    .isAllow())) {
                    return sendUnauthorizedStatus(exchange);
                }
                return chain.filter(exchange);
            })
            .onErrorResume(throwable -> sendUnauthorizedStatus(exchange)));
    }

    private Mono<Void> sendUnauthorizedStatus(final ServerWebExchange exchange) {
        exchange.getResponse()
            .setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse()
            .setComplete();
    }
}
