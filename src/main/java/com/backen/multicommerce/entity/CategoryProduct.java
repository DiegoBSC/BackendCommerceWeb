package com.backen.multicommerce.entity;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "pro_category_product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;

}
