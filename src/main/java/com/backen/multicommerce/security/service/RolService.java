package com.backen.multicommerce.security.service;

import com.backen.multicommerce.enums.EnumRol;
import com.backen.multicommerce.security.entity.Rol;
import com.backen.multicommerce.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(EnumRol rol){
    return rolRepository.findByName(rol);
    }
}
