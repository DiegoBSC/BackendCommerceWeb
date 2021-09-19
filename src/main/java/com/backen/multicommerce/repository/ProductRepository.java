package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {

    Boolean existsByName(String nameProduct);

//    List<Product> findByCompany(Company company);

//    @Query("SELECT c FROM Company c " +
//            "where cast(c.user.id as string)  = coalesce(cast(:userId as string), cast(c.user.id as string)) " +
//            "and c.status = 'ACT'" +
//            "and ((cast(UPPER(c.nameCompany) as string) like " +
//            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.nameCompany as string)))) " +
//            "or (cast(UPPER(c.identification) as string) like " +
//            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.identification as string)))))"
//    )
//    Page<Product> findByFilters(UUID userId, String mainFilter, Pageable pageable);

}
