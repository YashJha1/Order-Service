package com.eci.orderservice;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.eci.orderservice.client")
public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();

	}
	
	@Bean
        public Logger.Level feignLoggerLevel() {
        // FULL: logs headers, body, and metadata for both requests & responses
        	return Logger.Level.FULL;
    	}



	@Bean
    	public WebClient.Builder webClientBuilder() {
        	return WebClient.builder();
    	}


}
