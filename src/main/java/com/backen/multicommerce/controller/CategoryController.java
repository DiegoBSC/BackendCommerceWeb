package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CategoryProductPresenter;
import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.service.CategoryProductService;
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
@RequestMapping("/api/categoryProduct")
@CrossOrigin
public class CategoryController {
    
    @Autowired
    CategoryProductService categoryProductService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryProductPresenter categoryProductPresenter
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (categoryProductService.existsByName(categoryProductPresenter.getName()))
            return new ResponseEntity(new MessagePresenter("La categoria ya existe"), HttpStatus.BAD_REQUEST);
        categoryProductService.save(categoryProductPresenter);
        return new ResponseEntity(new MessagePresenter("El categoria fue guardada"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findCategoryById(@NotNull @RequestParam String categoryProductId) {
        if (categoryProductId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        CategoryProductPresenter categoryProductPresenter = categoryProductService.findById(UUID.fromString(categoryProductId));
        if(categoryProductPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Categoria no encontrada"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(categoryProductPresenter, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/findAll")
    public ResponseEntity<?> findCategoryAll() {
        List<CategoryProductPresenter> listResult = categoryProductService.findAll();
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @DeleteMapping("/delete")
    public void deleteCategoryById(@NotNull @RequestParam String categoryId) throws Exception {
        categoryProductService.deleteById(categoryId);
    }
}
