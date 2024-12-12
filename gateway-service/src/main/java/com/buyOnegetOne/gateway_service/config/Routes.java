package com.buyOnegetOne.gateway_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

//@Configuration(proxyBeanMethods = false)
public class Routes {

//    @Value("${product.service.url}")
//    private String productServiceUrl;
//
//    @Value("${inventory.service.url}")
//    private String inventoryServiceUrl;
//
//    @Value("${order.service.url}")
//    private String orderServiceUrl;
//
//    @Bean
//    public RouterFunction<ServerResponse> productServiceRoute() {
//        return route("product_service")
//                .route(RequestPredicates.path("/api/product/**"), http(productServiceUrl))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> inventoryServiceRoute() {
//        return route("inventory_service")
//                .route(RequestPredicates.path("/api/inventory/**"), http(inventoryServiceUrl))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> orderServiceRoute() {
//        return route("order_service")
//                .route(RequestPredicates.path("/api/order/**"), http(orderServiceUrl))
//                .build();
//    }

}
