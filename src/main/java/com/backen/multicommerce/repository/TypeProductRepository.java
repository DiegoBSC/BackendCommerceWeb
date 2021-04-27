package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.TypeProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TypeProductRepository extends CrudRepository<TypeProduct, UUID> {

    Boolean existsByName(String nameCategory);
}
