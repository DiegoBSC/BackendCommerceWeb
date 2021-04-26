package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.repository.CompanyRepository;
import com.backen.multicommerce.security.entity.User;
import com.backen.multicommerce.security.service.UserService;
import com.backen.multicommerce.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        return list.stream().map( (e)->
                getCompanyPresenterFromCompany(e)
        ).collect(Collectors.toList());
    }

    @Override
    public List<CompanyPresenter> findAllByUserId(String userId) {
        List<Company> list = (List<Company>) companyRepository.findByUser(new User(UUID.fromString(userId)));
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
    public CompanyPresenter save(CompanyPresenter companyPresenter) {
        User user  = userService.findById(companyPresenter.getUserId()).get();
        Company company = getCompanyFromCompanyPresenter(companyPresenter);
        company.setUser(user);
        company.setUpdateDate(new Date());
        Company companySave = companyRepository.save(company);
        CompanyPresenter companyPresenterResult = getCompanyPresenterFromCompany(companySave);
        return companyPresenterResult;
    }

    @Override
    public void deleteById(String companyId) throws Exception {
        Company company =  companyRepository.findById(UUID.fromString(companyId)).get();
        if(company == null)
            throw new Exception("La empresa no fue encontrada");
        company.setUpdateDate(new Date());
        company.setStatus(EnumStatusGeneral.INA);
        company.setNameCompany(company.getNameCompany().concat("-INA"));
        companyRepository.save(company);
    }

    @Override
    public CompanyPresenter getCompanyPresenterFromCompany(Company company){
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
    public Boolean existsByNameCompany(String nameCompany) {
        return companyRepository.existsByNameCompany(nameCompany);
    }

    @Override
    public Company getCompanyFromCompanyPresenter(CompanyPresenter companyPresenter){
        return Company.builder()
                .nameCompany(companyPresenter.getNameCompany())
                .id(companyPresenter.getId())
                .status(companyPresenter.getStatus() != null ? companyPresenter.getStatus() : EnumStatusGeneral.ACT)
                .createdDate(companyPresenter.getCreatedDate() == null ? new Date() : companyPresenter.getCreatedDate())
                .identification(companyPresenter.getIdentification())
                .build();
    }
}
