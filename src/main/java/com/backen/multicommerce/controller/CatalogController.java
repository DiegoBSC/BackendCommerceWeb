package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CatalogPresenter;
import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/catalogProduct")
@CrossOrigin
public class CatalogController {
    
    @Autowired
    CatalogService catalogService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveCatalog(@Valid @RequestBody CatalogPresenter catalogPresenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (catalogService.existsByNameCatalog(catalogPresenter.getName(), catalogPresenter.getId()))
            return new ResponseEntity(new MessagePresenter("El catálogo ya existe"), HttpStatus.BAD_REQUEST);
        catalogService.save(catalogPresenter);
        return new ResponseEntity(new MessagePresenter("El catálogo fue guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findCatalogById(@NotNull @RequestParam String catalogId) {
        if (catalogId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        CatalogPresenter catalogPresenter = catalogService.findById(UUID.fromString(catalogId));
        if(catalogPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Catálogo no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(catalogPresenter, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAllByCompany")
    public ResponseEntity<?> findCatalogAllByCompany(@NotNull @RequestParam String companyId) {
        if (companyId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        List<CatalogPresenter> listResult = catalogService.findByCompanyAndStatus(companyId);
        if(listResult == null)
            return new ResponseEntity(new MessagePresenter("Error: Catálogo no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAll")
    public ResponseEntity<?> findCatalogAll(@NotNull @RequestParam List<String> idsCompanies) {
        List<CatalogPresenter> listResult = catalogService.findAll(idsCompanies);
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @DeleteMapping("/delete")
    public void deleteCatalogById(@NotNull @RequestParam String catalogId) throws Exception {
        catalogService.deleteById(catalogId);
    }
}
