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
public class TaxProductPresenter {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal valueTax;
    private EnumStatusGeneral status;
}
