package com.rktech.product_service.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity 
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
