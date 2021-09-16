package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.TaxProduct;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TaxProductRepository extends CrudRepository<TaxProduct, UUID> {

    Boolean existsByName(String nameCategory);

    List<TaxProduct> findByStatus(EnumStatusGeneral status);
}
