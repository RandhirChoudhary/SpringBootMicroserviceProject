package com.rktech.inventory_service.controller;

import com.rktech.inventory_service.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.rktech.inventory_service.service.InventoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/inventory")
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus (HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
