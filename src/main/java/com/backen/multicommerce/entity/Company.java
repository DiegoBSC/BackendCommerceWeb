package com.backen.multicommerce.entity;

import com.backen.multicommerce.enums.EnumStatusGeneral;
import com.backen.multicommerce.security.entity.User;
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
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @NotNull
    @Column(unique = true)
    private String nameCompany;
    @NotNull
    private String identification;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumStatusGeneral status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Company(UUID id) {
        this.id = id;
    }
}
