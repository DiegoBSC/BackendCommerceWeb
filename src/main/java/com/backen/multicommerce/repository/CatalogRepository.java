package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Catalog;
import com.backen.multicommerce.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CatalogRepository extends CrudRepository<Catalog, UUID> {

    Boolean existsByName(String nameCatalog);

    List<Catalog> findByCompany(Company company);
}
