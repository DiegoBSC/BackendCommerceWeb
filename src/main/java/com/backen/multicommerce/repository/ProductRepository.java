package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {

    // @Query("select count(e)>0 from Product e where e.name = ?1 AND e.company ")
    Boolean existsByNameAndCompany(String nameProduct, Company company);

    List<Product> findByCompany(Company company);
}
