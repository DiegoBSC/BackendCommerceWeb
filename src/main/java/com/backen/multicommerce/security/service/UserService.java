package com.backen.multicommerce.security.service;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.enums.EnumRol;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.security.entity.Rol;
import com.backen.multicommerce.security.entity.User;
import com.backen.multicommerce.security.presenter.UserPresenter;
import com.backen.multicommerce.security.repository.UserRepository;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService rolService;

    public Optional<User> getByNick(String userNick) {
        return userRepository.findByNick(userNick);
    }

    public Boolean existByNickUser(String userNick) {
        return userRepository.existsByNick(userNick);
    }

    public Boolean existByEmailUser(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }

    public void save(UserPresenter userPresenter) throws Exception {
        User user = User.builder()
                .email(userPresenter.getEmail())
                .name(userPresenter.getName())
                .nick(userPresenter.getNick())
                .password(passwordEncoder.encode(userPresenter.getPassword()))
                .createdDate(new Date())
                .status(EnumStatusGeneral.ACT)

                .build();
        Set<Rol> roles = new HashSet<>();
        if (userPresenter.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(EnumRol.ROLE_ADMIN).get());
        if (userPresenter.getRoles().contains("super"))
            roles.add(rolService.getByRolName(EnumRol.ROLE_SUPER).get());
        if (userPresenter.getRoles().contains("customer"))
            roles.add(rolService.getByRolName(EnumRol.ROLE_CUSTOMER).get());
        if (userPresenter.getRoles().contains("user"))
            roles.add(rolService.getByRolName(EnumRol.ROLE_USER).get());
        if (roles.isEmpty())
            throw new Exception("El usuario debe tener al menos un rol");
        user.setRoles(roles);
        userRepository.save(user);
    }

    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public UserPresenter getByNickPresenter(String userNick) {
        User user = userRepository.findByNick(userNick).get();
        return getUserPresenterFromUser(user);
    }


    public Paginator findUserFilter(Integer page, Integer size, String mainFilter) {
        Pageable paging = PageRequest.of(page, size, Sort.by("nick"));

        Page<User> listQuery = userRepository.findByFilters(mainFilter, paging);

        List<UserPresenter> listPresenter = listQuery.getContent().stream().map((e) ->
                getUserPresenterFromUser(e)
        ).collect(Collectors.toList());

        Paginator paginator = new Paginator(listQuery.getTotalPages(), listQuery.getTotalElements(), Set.of(listPresenter));
        return paginator;
    }

    public UserPresenter getUserPresenterFromUser(User user) {
        Set<String> listRoles = new HashSet<>();
        Set<String> companies = new HashSet<>();

        user.getRoles().forEach(rol -> {
            listRoles.add(rol.getName().name());
        });

        user.getCompanies().forEach(company -> {
            companies.add(company.getId().toString());
        });

        return UserPresenter.builder()
                .id(user.getId())
                .nick(user.getNick())
                .email(user.getEmail())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(listRoles)
                .companies(companies)
                .build();
    }


}
