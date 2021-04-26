package com.backen.multicommerce.security.presenter;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class UserPresenter {
    @NotBlank
    private String name;
    @NotBlank
    private String nick;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Set<String> roles;
}
