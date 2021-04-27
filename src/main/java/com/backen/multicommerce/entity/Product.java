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
@Table(name = "pro_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    @Column(unique = true)
    private String code;
    private String description;
    @NotNull
    private BigDecimal purchasePrice;
    @NotNull
    private BigDecimal salePrice;
    private String image;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_product_id", referencedColumnName = "id")
    private TypeProduct typeProduct;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_product_id", referencedColumnName = "id")
    private CategoryProduct categoryProduct;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_product_id", referencedColumnName = "id")
    private TaxProduct taxProduct;
}
