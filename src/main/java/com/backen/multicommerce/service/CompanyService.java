package com.backen.multicommerce.service;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    List<CompanyPresenter> findAll();
    CompanyPresenter findById(UUID id);
    CompanyPresenter save(CompanyPresenter companyPresenter);
    void deleteById(String id) throws Exception;
    Company getCompanyFromCompanyPresenter(CompanyPresenter companyPresenter);
    CompanyPresenter getCompanyPresenterFromCompany(Company company);
    Boolean existsByNameCompany(String nameCompany);
    Paginator findCompanyFilter(Integer page, Integer size, String mainFilter, String userId);
}
