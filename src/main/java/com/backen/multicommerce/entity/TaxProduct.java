package com.backen.multicommerce.entity;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pro_tax_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal valueTax;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;

}
