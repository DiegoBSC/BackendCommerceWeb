package com.backen.multicommerce.security.entity;

import com.backen.multicommerce.enums.EnumRol;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "sec_roles")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumRol name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
}
