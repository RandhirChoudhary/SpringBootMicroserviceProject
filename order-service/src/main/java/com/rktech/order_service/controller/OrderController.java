package com.rktech.order_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rktech.order_service.dto.OrderRequest;
import com.rktech.order_service.service.OrderService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/order") 
public class OrderController {
    private final OrderService orderService;
    @PostMapping 
    @ResponseStatus (HttpStatus.CREATED)
    //for fallback testing after circuit break
    @CircuitBreaker(name="inventory",fallbackMethod = "fallBackMethod")
    //for timeout testing
    @TimeLimiter(name="inventory")
    //For retry testing
    @Retry(name="inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));

    }
    //Method will be called after if the circuit breaks
    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! something went wrong, Please order after sometimes!");
    }
    
}
