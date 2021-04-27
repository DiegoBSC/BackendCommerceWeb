package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.presenter.TypeProductPresenter;
import com.backen.multicommerce.service.TypeProductService;
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
@RequestMapping("/typeProduct")
@CrossOrigin
public class TypeController {
    
    @Autowired
    TypeProductService typeProductService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveType(@Valid @RequestBody TypeProductPresenter typeProductPresenter
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (typeProductService.existsByName(typeProductPresenter.getName()))
            return new ResponseEntity(new MessagePresenter("La tipo producto ya existe"), HttpStatus.BAD_REQUEST);
        typeProductService.save(typeProductPresenter);
        return new ResponseEntity(new MessagePresenter("El tipo producto fue guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findTypeById(@NotNull @RequestParam String typeProductId) {
        if (typeProductId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        TypeProductPresenter typeProductPresenter = typeProductService.findById(UUID.fromString(typeProductId));
        if(typeProductPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Tipo de producto no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(typeProductPresenter, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAll")
    public ResponseEntity<?> findTypeAll() {
        List<TypeProductPresenter> listResult = typeProductService.findAll();
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @GetMapping("/delete")
    public void deleteTypeById(@NotNull @RequestParam String typeId) throws Exception {
        typeProductService.deleteById(typeId);
    }
}
