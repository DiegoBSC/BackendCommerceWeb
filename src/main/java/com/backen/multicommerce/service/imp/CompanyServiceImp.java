package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.repository.CompanyRepository;
import com.backen.multicommerce.security.entity.User;
import com.backen.multicommerce.security.service.UserService;
import com.backen.multicommerce.service.CompanyService;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserService userService;

    @Override
    public List<CompanyPresenter> findAll() {
        List<Company> list = (List<Company>) companyRepository.findAll();
        return list.stream().map((e) ->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());
    }

    @Override
    public Paginator findCompanyFilter(Integer page, Integer size, String mainFilter, String userId) {
        Pageable paging = PageRequest.of(page, size, Sort.by("nameCompany"));

        Page<Company> listQuery = companyRepository.findByFilters(userId != null ? UUID.fromString(userId) : null, mainFilter, paging);

        List<CompanyPresenter> listPresenter = listQuery.getContent().stream().map((e) ->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());

        Paginator paginator = new Paginator(listQuery.getTotalPages(), listQuery.getTotalElements(), Set.of(listPresenter));
        return paginator;
    }

    @Override
    public List<CompanyPresenter> findCompanyByIdUser(String userId) {

        List<Company> listQuery = companyRepository.findByUserIdAndStatus(UUID.fromString(userId), EnumStatusGeneral.ACT );

        List<CompanyPresenter> listPresenter = listQuery.stream().map((e) ->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());

        return listPresenter;
    }

    @Override
    public List<CompanyPresenter> findCompaniesByUser(List<String> companiesIds) {
        List<UUID> ids = companiesIds.stream().map((item) -> UUID.fromString(item)).collect(Collectors.toList());
        List<Company> listQuery = companyRepository.findByIdInAndStatus(ids, EnumStatusGeneral.ACT);
        return listQuery.stream().map((e) ->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());
    }

    @Override
    public CompanyPresenter findById(UUID id) {
        CompanyPresenter companyPresenter = null;
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            companyPresenter = getCompanyPresenterFromCompany(company);
        }
        return companyPresenter;
    }

    @Override
    public CompanyPresenter save(CompanyPresenter companyPresenter) throws Exception {
        User user = userService.findById(companyPresenter.getUserId()).get();
        Company company = getCompanyFromCompanyPresenter(companyPresenter);
        company.setUser(user);
        company.setUpdateDate(new Date());
        Company companySave = companyRepository.save(company);
        user.getCompanies().add(companySave);
        userService.save(userService.getUserPresenterFromUser(user));
        CompanyPresenter companyPresenterResult = getCompanyPresenterFromCompany(companySave);
        return companyPresenterResult;
    }

    @Override
    public void deleteById(String companyId) throws Exception {
        Company company = companyRepository.findById(UUID.fromString(companyId)).get();
        if (company == null)
            throw new Exception("La empresa no fue encontrada");
        company.setUpdateDate(new Date());
        company.setStatus(EnumStatusGeneral.INA);
        company.setNameCompany(company.getNameCompany().concat("-INA"));
        companyRepository.save(company);
    }

    @Override
    public CompanyPresenter getCompanyPresenterFromCompany(Company company) {
        return CompanyPresenter.builder()
                .nameCompany(company.getNameCompany())
                .id(company.getId())
                .status(company.getStatus())
                .createdDate(company.getCreatedDate())
                .identification(company.getIdentification())
                .userId(company.getUser().getId())
                .build();
    }

    @Override
    public Boolean existsByNameCompany(String nameCompany, UUID id) {
        if (id != null) {
            return companyRepository.existsByNameCompanyAndId(nameCompany, id);
        }
        return !companyRepository.existsByNameCompany(nameCompany);
    }

    @Override
    public Company getCompanyFromCompanyPresenter(CompanyPresenter companyPresenter) {
        return Company.builder()
                .nameCompany(companyPresenter.getNameCompany())
                .id(companyPresenter.getId())
                .status(companyPresenter.getStatus() != null ? companyPresenter.getStatus() : EnumStatusGeneral.ACT)
                .createdDate(companyPresenter.getId() == null ? new Date() : companyPresenter.getCreatedDate())
                .updateDate(companyPresenter.getId() == null ? null : new Date())
                .identification(companyPresenter.getIdentification())
                .build();
    }
}
