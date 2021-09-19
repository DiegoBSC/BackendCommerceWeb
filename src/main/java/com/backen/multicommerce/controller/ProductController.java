package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.presenter.ProductPresenter;
import com.backen.multicommerce.service.ProductService;
import com.backen.multicommerce.utils.Paginator;
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
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    
    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductPresenter productPresenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        if (productPresenter.getId() == null &&  productService.existsByName(productPresenter.getName()))
            return new ResponseEntity(new MessagePresenter("El producto ya existe"), HttpStatus.BAD_REQUEST);
        productService.save(productPresenter);
        return new ResponseEntity(new MessagePresenter("El producto fue guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/find")
    public ResponseEntity<?> findProductById(@NotNull @RequestParam String productId) {
        if (productId == null)
            return new ResponseEntity(new MessagePresenter("Datos inválidos"), HttpStatus.BAD_REQUEST);
        ProductPresenter productPresenter = productService.findById(UUID.fromString(productId));
        if(productPresenter == null)
            return new ResponseEntity(new MessagePresenter("Error: Producto no encontrado"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(productPresenter, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/list")
    public ResponseEntity<?> findProduct(@NotNull @RequestParam Integer page,
                                                  @NotNull @RequestParam Integer size,
                                                  @RequestParam(required = false) String mainFilter) {
        Paginator listResult = productService.findProductFilter(page, size, mainFilter);
        if (listResult == null)
            return new ResponseEntity(new MessagePresenter("Error: Productos no encontrados"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @GetMapping("/delete")
    public void deleteProductById(@NotNull @RequestParam String productId) throws Exception {
        productService.deleteById(productId);
    }
}
