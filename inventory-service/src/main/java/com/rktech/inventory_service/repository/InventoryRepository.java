package com.rktech.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rktech.inventory_service.model.Inventory;
@Repository 
public interface InventoryRepository extends JpaRepository<Inventory,Long>{
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
