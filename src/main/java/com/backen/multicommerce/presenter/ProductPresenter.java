package com.backen.multicommerce.presenter;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPresenter {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String code;
    private String description;
    @NotNull
    private BigDecimal purchasePrice;
    @NotNull
    private BigDecimal salePrice;
    private String image;
    private EnumStatusGeneral status;
    @NotNull
    private TypeProductPresenter typeProduct;
    @NotNull
    private CategoryProductPresenter categoryProduct;
    @NotNull
    private TaxProductPresenter taxProduct;

    private String companyId;
}
