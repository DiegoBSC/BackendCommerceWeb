package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.CategoryProduct;
import com.backen.multicommerce.entity.TypeProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TypeProductRepository extends CrudRepository<TypeProduct, UUID> {

    Boolean existsByName(String nameCategory);
    List<TypeProduct> findByStatus(EnumStatusGeneral status);
}
