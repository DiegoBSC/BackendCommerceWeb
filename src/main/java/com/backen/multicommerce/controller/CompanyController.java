package com.backen.multicommerce.controller;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public List<CompanyPresenter> getListCompanies(){
        return companyService.findAll();
    }

    @GetMapping("/companies/id")
    public CompanyPresenter getCompanyById(@RequestParam("id") String id){
        return companyService.findById(UUID.fromString(id));
    }

    @DeleteMapping("/companies/id")
    public void deleteCompanyById(@RequestParam("id") String id){
        companyService.deleteById(UUID.fromString(id));
    }

    @PostMapping("/companies")
    public CompanyPresenter saveCompany(@RequestBody Company company){
        return companyService.save(company);
    }

}
