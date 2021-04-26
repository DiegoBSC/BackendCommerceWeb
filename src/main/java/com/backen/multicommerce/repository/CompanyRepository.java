package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.security.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends CrudRepository<Company, UUID> {

    Boolean existsByNameCompany(String nameCompany);

    List<Company> findByUser(User user);

}
