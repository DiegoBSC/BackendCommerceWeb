package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryProductRepository extends CrudRepository<Product, UUID> {
}
