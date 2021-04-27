package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.CategoryProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryProductRepository extends CrudRepository<CategoryProduct, UUID> {

    Boolean existsByName(String nameCategory);

}
