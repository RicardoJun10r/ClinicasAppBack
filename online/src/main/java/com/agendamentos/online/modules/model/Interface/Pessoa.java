package com.agendamentos.online.modules.model.Interface;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Pessoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_col")
    private UUID uuid;

    @Column(name = "name_col")
    private String name;

    @Column(name = "created_col")
    private LocalDate createdAt;

}
