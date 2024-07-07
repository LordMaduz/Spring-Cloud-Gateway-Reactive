package com.spring.cloud.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.spring.cloud.model.ValidationAccessHeaders;
import com.spring.cloud.vo.ResponseVo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CedarAccessValidationHandler {

    private final WebClient.Builder webclientBuilder;
    @Value("${cedar.endpoint}")
    private String authorizationEndpoint;

    public Mono<ResponseVo> fetchAuthorizationResponse(final String service, final String principle, final String action, final String resource,
        final String context) {
        return webclientBuilder.build()
            .post()
            .uri(authorizationEndpoint)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .headers(h -> h.addAll(createHttpHeaders(service, principle, action, resource, context)))
            .retrieve()
            .bodyToMono(ResponseVo.class);
    }

    private HttpHeaders createHttpHeaders(final String service, final String principle, final String action, final String resource, final String context) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ValidationAccessHeaders.SERVICE_ID.getType(), service);
        headers.add(ValidationAccessHeaders.SECURITY_GROUP.getType(), principle);
        headers.add(ValidationAccessHeaders.ACTION_ID.getType(), action);
        headers.add(ValidationAccessHeaders.RESOURCE_ID.getType(), resource);
        headers.add(ValidationAccessHeaders.CONTEXT.getType(), context);

        return headers;
    }

}
