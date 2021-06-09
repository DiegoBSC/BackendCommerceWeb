package com.backen.multicommerce.controller;

import com.backen.multicommerce.presenter.CompanyPresenter;
import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.security.presenter.UserPresenter;
import com.backen.multicommerce.security.service.UserService;
import com.backen.multicommerce.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/createUser")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserPresenter userPresenter, BindingResult bindingResult) throws Exception {
        // BindingResult es lo que maneja los posibles errores del objeto  @NotNull, @NotBlank, etc
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos invalidos"), HttpStatus.BAD_REQUEST);
        if (userPresenter.getId() == null && userService.existByNickUser(userPresenter.getNick()))
            return new ResponseEntity(new MessagePresenter("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if (userPresenter.getId() == null && userService.existByEmailUser(userPresenter.getEmail()))
            return new ResponseEntity(new MessagePresenter("El email ya existe"), HttpStatus.BAD_REQUEST);
        userService.save(userPresenter);
        return new ResponseEntity(new MessagePresenter("El usuario fue guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'USER')")
    @GetMapping("/list")
    public ResponseEntity<?> allUsers(@NotNull @RequestParam Integer page,
                                                  @NotNull @RequestParam Integer size,
                                                  @RequestParam(required = false) String mainFilter) {
        Paginator listResult = userService.findUserFilter(page, size, mainFilter);
        if(listResult == null)
            return new ResponseEntity(new MessagePresenter("Error: Usarios no registrados"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(listResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER')")
    @PostMapping("/delete")
    public void deleteUserById(@NotNull @RequestBody UserPresenter userPresenter) throws Exception {
        userService.deleteUserById(userPresenter.getId().toString());
    }
}
