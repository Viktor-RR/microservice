package com.example.gateway.filter;

import com.example.gateway.exceptions.InvalidAuthException;
import com.example.gateway.service.AuthenticateService;
import com.google.common.net.HttpHeaders;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;

@Component
@CommonsLog
public class TokenFilter implements GlobalFilter {
    public static final String INVALID_TOKEN = "Invalid token";
    private final AuthenticateService serviceFilter;

    public TokenFilter(AuthenticateService serviceFilter) {
        this.serviceFilter = serviceFilter;
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String host = ((Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)).getUri().getHost();
        if (host.equals("user")) {
            return chain.filter(exchange);
        }

        final String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (token == null) {
            throw new InvalidAuthException(INVALID_TOKEN);
        }
        final RSAPublicKey publicKey = serviceFilter.getRsaPublicKey();
        final SignedJWT parsedToken = SignedJWT.parse(token);
        final String dataToHeader = serviceFilter.getData(parsedToken);
        serviceFilter.checkTokenExpirationDate(parsedToken);
        final RSASSAVerifier verifier = new RSASSAVerifier(publicKey);
        final boolean verify = parsedToken.verify(verifier);
        if (verify) {
            exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION,dataToHeader);
            return chain.filter(exchange);
        } else {
            throw new InvalidAuthException(INVALID_TOKEN);
        }
    }
}
