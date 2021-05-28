package com.backen.multicommerce.repository;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends CrudRepository<Company, UUID> {

    Boolean existsByNameCompanyAndId(String nameCompany, UUID id);

    Boolean existsByNameCompany(String nameCompany);

    List<Company> findByUser(User user);

    @Query("SELECT c FROM Company c " +
    "where cast(c.user.id as string)  = coalesce(cast(:userId as string), cast(c.user.id as string)) " +
    "and c.status = 'ACT'" +
    "and ((cast(UPPER(c.nameCompany) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.nameCompany as string)))) " +
    "or (cast(UPPER(c.identification) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.identification as string)))))"
    )
    Page<Company> findByFilters(UUID userId, String mainFilter, Pageable pageable);

    List<Company> findByUserId(UUID userId);

}
