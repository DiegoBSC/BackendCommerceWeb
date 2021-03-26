package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.repository.CompanyRepository;
import com.backen.multicommerce.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public List<CompanyPresenter> findAll() {
        List<Company> list = (List<Company>) companyRepository.findAll();
        return list.stream().map( (e)->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());
    }

    @Override
    public CompanyPresenter findById(UUID id) {
        CompanyPresenter companyPresenter = null;
        Company company = companyRepository.findById(id).orElse(null);
        if(company != null){
            companyPresenter = getCompanyPresenterFromCompany(company);
        }
        return companyPresenter;
    }

    @Override
    public CompanyPresenter save(Company company) {
        Company companySave = companyRepository.save(company);
        CompanyPresenter companyPresenter = getCompanyPresenterFromCompany(companySave);
        return companyPresenter;
    }

    @Override
    public void deleteById(UUID id) {
        companyRepository.deleteById(id);
    }

    public CompanyPresenter getCompanyPresenterFromCompany(Company company){
        return CompanyPresenter.builder()
                        .nameCompany(company.getNameCompany())
                        .id(company.getId())
                        .active(company.getActive())
                        .createdDate(company.getCreatedDate())
                        .identification(company.getIdentification())
                        .build();
    }
}
