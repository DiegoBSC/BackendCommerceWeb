package com.backen.multicommerce.presenter;

import com.backen.multicommerce.entity.Company;
import com.backen.multicommerce.entity.Product;
import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogPresenter {
    private UUID id;
    private Date createdDate;
    @NotNull
    private String name;
    private EnumStatusGeneral status;
    private Set<Product> products;
    private UUID companyId;
}
