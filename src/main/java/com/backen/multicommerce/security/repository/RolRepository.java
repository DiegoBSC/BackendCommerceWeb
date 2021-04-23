package com.backen.multicommerce.security.repository;

import com.backen.multicommerce.enums.EnumRol;
import com.backen.multicommerce.security.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {

    Optional<Rol> findByName(EnumRol rolName);
}
