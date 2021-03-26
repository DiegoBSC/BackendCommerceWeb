package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CompanyRepository extends CrudRepository<Company, UUID> {
}
