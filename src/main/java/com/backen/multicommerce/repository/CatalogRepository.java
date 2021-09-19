package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Catalog;
import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CatalogRepository extends CrudRepository<Catalog, UUID> {

    Boolean existsByName(String nameCatalog);

    @Query("SELECT c FROM Catalog c " +
            "where c.company.id in (:companiesIds)" +
            "and c.status = 'ACT'"
    )
    List<Catalog> findCatalogByUser(List<UUID> companiesIds);

    List<Catalog> findByCompanyAndStatus(Company company, EnumStatusGeneral status);
}
