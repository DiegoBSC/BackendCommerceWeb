package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Catalog;
import com.backen.multicommerce.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CatalogRepository extends CrudRepository<Catalog, UUID> {

    Boolean existsByName(String nameCatalog);

    List<Catalog> findByCompany(Company company);

    @Query("FROM Catalog c" +
            " JOIN c.company o" +
            " WHERE c.company.id = :companyId")
    List<Catalog> findByCatalogByCompanyId(@Param("companyId") UUID companyId);
}
