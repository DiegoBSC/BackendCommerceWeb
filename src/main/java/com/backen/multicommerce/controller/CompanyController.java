package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.service.CompanyService;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveCompany(@Valid @RequestBody CompanyPresenter companyPresenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (!companyService.existsByNameCompany(companyPresenter.getNameCompany(), companyPresenter.getId()))
            return new ResponseEntity(new MessagePresenter("La empresa ya existe"), HttpStatus.BAD_REQUEST);
        companyService.save(companyPresenter);
        return new ResponseEntity(new MessagePresenter("El empresa fue guardada"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findCompanyById(@NotNull @RequestParam String companyId) {
        if (companyId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        CompanyPresenter companyPresenter = companyService.findById(UUID.fromString(companyId));
        if(companyPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Empresa no encontrada"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(companyPresenter, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/list")
    public ResponseEntity<?> findCompanyAllByUser(@NotNull @RequestParam Integer page,
                                                  @NotNull @RequestParam Integer size,
                                                  @RequestParam(required = false) String mainFilter,
                                                  @RequestParam(required = false) String userId) {
        Paginator listResult = companyService.findCompanyFilter(page, size, mainFilter, userId);
        if(listResult == null)
            return new ResponseEntity(new MessagePresenter("Error: Empresa no encontrada"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAll")
    public ResponseEntity<?> findCompanyAll() {
        List<CompanyPresenter> listResult = companyService.findAll();
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/delete")
    public void deleteCompanyById(@NotNull @RequestBody CompanyPresenter companyPresenter) throws Exception {
        companyService.deleteById(companyPresenter.getId().toString());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findByUserId")
    public ResponseEntity<?> findCompaniesByIdUser(@NotNull @RequestParam String userId) {
        if (userId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        List<CompanyPresenter> listCompanies = companyService.findCompanyByIdUser(userId);
        return new ResponseEntity(listCompanies, HttpStatus.OK);
    }
}
