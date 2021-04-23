package com.backen.multicommerce.security.entity;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Table(name = "sec_users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private Date createdDate;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String nick;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns= @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles;
}
