package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.TaxProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TaxProductRepository extends CrudRepository<TaxProduct, UUID> {

    Boolean existsByName(String nameCategory);
}
