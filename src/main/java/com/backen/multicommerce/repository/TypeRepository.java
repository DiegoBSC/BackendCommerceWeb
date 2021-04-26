package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.TypeProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TypeRepository extends CrudRepository<TypeProduct, UUID> {
}
