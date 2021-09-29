package com.backen.multicommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "gen_setting_app")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationApp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    @Column(unique = true)
    private String type;

    @NotNull
    @Column(unique = true)
    private String value;

    @NotNull
    @Column(unique = true)
    private String status;
}
