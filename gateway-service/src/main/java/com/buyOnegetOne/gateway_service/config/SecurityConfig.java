package com.buyOnegetOne.gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/aggregate/**",
            "/webjars/**",
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

            http.csrf()
                    .disable()
                    .authorizeExchange((exchanges) -> exchanges
                            .pathMatchers("/eureka/**")
                            .permitAll()
                            .pathMatchers(AUTH_WHITELIST)
                            .permitAll()
                            .pathMatchers(HttpMethod.OPTIONS, "/**")
                            .permitAll() // Allow all preflight requests
                            .anyExchange()
                            .authenticated())
                    .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
            return http.build();
    }
}

