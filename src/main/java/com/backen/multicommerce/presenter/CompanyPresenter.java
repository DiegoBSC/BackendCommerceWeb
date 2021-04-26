package com.backen.multicommerce.presenter;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPresenter {

    private UUID id;
    private Date createdDate;
    @NotNull
    private String nameCompany;
    @NotNull
    private String identification;
    private EnumStatusGeneral status;
    @NotNull
    private UUID userId;
}
