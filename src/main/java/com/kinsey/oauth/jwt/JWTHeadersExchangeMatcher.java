package com.kinsey.oauth.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by zj on 2019/1/13
 */
public class JWTHeadersExchangeMatcher implements ServerWebExchangeMatcher {

    @Override
    public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
        Mono<ServerHttpRequest> request = Mono.just(serverWebExchange).map(ServerWebExchange::getRequest);

        return request.map(ServerHttpRequest::getHeaders)
                .filter(h -> h.containsKey(HttpHeaders.AUTHORIZATION))
                .flatMap(o -> MatchResult.match())
                .switchIfEmpty(MatchResult.notMatch());
    }
}
