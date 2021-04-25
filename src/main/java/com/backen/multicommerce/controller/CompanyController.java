package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/companies")
    public CompanyPresenter saveCompany(@RequestBody CompanyPresenter companyPresenter){
        return companyService.save(companyPresenter);
    }

}
