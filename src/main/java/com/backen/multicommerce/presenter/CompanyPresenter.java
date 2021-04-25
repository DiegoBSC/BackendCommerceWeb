package com.backen.multicommerce.presenter;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPresenter {

    private UUID id;
    private Date createdDate;
    private String nameCompany;
    private String identification;
    private EnumStatusGeneral status;
}
