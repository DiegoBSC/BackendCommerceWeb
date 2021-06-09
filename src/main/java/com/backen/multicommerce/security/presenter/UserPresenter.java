package com.backen.multicommerce.security.presenter;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserPresenter {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String nick;
    @Email
    private String email;
    private String password;
    private EnumStatusGeneral status;
    @NotNull
    private Set<String> roles;
    @NotNull
    private Set<String> companies;
}
