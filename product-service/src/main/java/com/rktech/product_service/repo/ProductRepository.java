package com.rktech.product_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rktech.product_service.model.Product;
@Repository 
public interface ProductRepository extends JpaRepository<Product,String>{

}
