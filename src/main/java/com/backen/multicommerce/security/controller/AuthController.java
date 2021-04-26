package com.backen.multicommerce.security.controller;

import com.backen.multicommerce.presenter.MessagePresenter;
import com.backen.multicommerce.security.jwt.JwtProvider;
import com.backen.multicommerce.security.presenter.JwtPresenter;
import com.backen.multicommerce.security.presenter.LoginUserPresenter;
import com.backen.multicommerce.security.presenter.UserPresenter;
import com.backen.multicommerce.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/public")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/createUser")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserPresenter userPresenter, BindingResult bindingResult) throws Exception {
        // BindingResult es lo que maneja los posibles errores del objeto  @NotNull, @NotBlank, etc
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Datos invalidos"), HttpStatus.BAD_REQUEST);
        if (userService.existByNickUser(userPresenter.getNick()))
            return new ResponseEntity(new MessagePresenter("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if (userService.existByEmailUser(userPresenter.getEmail()))
            return new ResponseEntity(new MessagePresenter("El email ya existe"), HttpStatus.BAD_REQUEST);
        userService.save(userPresenter);
        return new ResponseEntity(new MessagePresenter("El usuario fue guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtPresenter> login(@Valid @RequestBody LoginUserPresenter loginUserPresenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessagePresenter("Usuario o Contraseña inválidos"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserPresenter.getNick(), loginUserPresenter.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtPresenter jwtPresenter = JwtPresenter.builder()
                .token(jwt)
                .nick(userDetails.getUsername())
                .authorities(userDetails.getAuthorities())
                .build();
        return new ResponseEntity(jwtPresenter, HttpStatus.OK);
    }

}
