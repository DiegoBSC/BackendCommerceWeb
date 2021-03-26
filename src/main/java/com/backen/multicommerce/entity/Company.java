package com.backen.multicommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "com_companies")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @NotNull
    private Date createdDate;
    @NotNull
    private String nameCompany;
    @NotNull
    private String identification;
    @NotNull
    private Boolean active;

}
