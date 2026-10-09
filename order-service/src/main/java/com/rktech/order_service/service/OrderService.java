package com.rktech.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.rktech.order_service.dto.InventoryResponse;
import com.rktech.order_service.event.OrderPlacedEvent;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rktech.order_service.dto.OrderLineItemsDto;
import com.rktech.order_service.dto.OrderRequest;
import com.rktech.order_service.model.Order;
import com.rktech.order_service.model.OrderLineItems;
import com.rktech.order_service.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Service 
@Slf4j
@RequiredArgsConstructor
@Transactional 
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    //Tracer is used to create our own spanId for tracing
    private final Tracer tracer;
    //KafkaTemplate is used to produce messages on kafka topics
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
        .toList();
        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes= order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();
        Span inventoryServiceLookup = tracer.nextSpan().name("inventoryServiceLookup");
        try(Tracer.SpanInScope spanInScope= tracer.withSpan(inventoryServiceLookup.start())){
            //Call Inventory service and place order if product is in stock
            InventoryResponse[] inventoryResponses = webClientBuilder.build().get().
                    uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes)
                            .build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class).block();

            boolean allProductIsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);
            if(allProductIsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
                log.info("Order {} saved",order.getId());
                return "Order Placed Successfully";
            }else{
                throw new IllegalArgumentException("Product is not in stock, Please try again later!");
            }
        }finally {
            inventoryServiceLookup.end();
        }


    }
    
    public OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
        
    }
}
