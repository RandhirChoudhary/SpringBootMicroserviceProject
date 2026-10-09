package com.rktech.product_service.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rktech.product_service.dto.ProductRequest;
import com.rktech.product_service.dto.ProductResponse;
import com.rktech.product_service.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
	private final ProductService productService;
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createProduct(@RequestBody ProductRequest request) {

		productService.createProduct(request);
		return "Product Saved Successfully";
	}
	@GetMapping 
	@ResponseStatus (HttpStatus.OK)
	public List<ProductResponse> getAllProducts(){

		return productService.getAllProducts();
	}

}
