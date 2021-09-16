package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.CategoryProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryProductRepository extends CrudRepository<CategoryProduct, UUID> {

    Boolean existsByName(String nameCategory);
    List<CategoryProduct> findByStatus(EnumStatusGeneral status);

}
