package com.backen.multicommerce.security.repository;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.security.entity.User;
import com.backen.multicommerce.security.presenter.UserPresenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByNick(String userNick);

    Boolean existsByNick(String userNick);

    Boolean existsByEmail(String userEmail);

    @Query("SELECT c FROM User c " +
            "where c.status = 'ACT' " +
            "and ((cast(UPPER(c.nick) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.nick as string)))) " +
            "or (cast(UPPER(c.name) as string) like " +
            "UPPER(coalesce(cast(CONCAT('%', :mainFilter,'%') as string), cast(c.name as string)))))"
    )
    Page<User> findByFilters(String mainFilter, Pageable pageable);
}
