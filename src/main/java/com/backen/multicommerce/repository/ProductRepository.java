package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {

    Boolean existsByName(String nameProduct);

//    List<Product> findByCompany(Company company);

    @Query("SELECT p FROM Product p " +
            "where ((cast(UPPER(p.name) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(p.name as string)))) " +
            "or (cast(UPPER(p.code) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(p.code as string)))))"
    )
    Page<Product> findByFilters(String mainFilter, Pageable pageable);

}
