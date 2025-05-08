package com.microservicewebclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
//
//
//
//
//@Configuration
//public class WebClientConfig {
//
//    @Bean
//    public WebClient customerWebClient() {
//        return WebClient.create("http://localhost:9091"); // Customer service base URL
//    }
//
//    @Bean
//    public WebClient productWebClient() {
//        return WebClient.create("http://localhost:9092"); // Product service base URL
//    }