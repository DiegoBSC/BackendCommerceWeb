package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CatalogPresenter;
import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.presenter.TaxProductPresenter;
import com.backen.multicommerce.service.TaxProductService;
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
@RequestMapping("/api/taxProduct")
@CrossOrigin
public class TaxController {
    
    @Autowired
    TaxProductService taxProductService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveTaxProduct(@Valid @RequestBody TaxProductPresenter taxProductPresenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (taxProductService.existsByName(taxProductPresenter.getName()))
            return new ResponseEntity(new MessagePresenter("El catalogo ya existe"), HttpStatus.BAD_REQUEST);
        taxProductService.save(taxProductPresenter);
        return new ResponseEntity(new MessagePresenter("El catalogo fue guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findTaxProductById(@NotNull @RequestParam String taxProductId) {
        if (taxProductId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        TaxProductPresenter taxProductPresenter = taxProductService.findById(UUID.fromString(taxProductId));
        if(taxProductPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Impuesto no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(taxProductPresenter, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAll")
    public ResponseEntity<?> findTaxProductAll() {
        List<TaxProductPresenter> listResult = taxProductService.findAll();
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @DeleteMapping("/delete")
    public void deleteCatalogById(@NotNull @RequestParam String taxProductId) throws Exception {
        taxProductService.deleteById(taxProductId);
    }
}
