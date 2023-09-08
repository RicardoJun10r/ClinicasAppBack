package com.agendamentos.online.modules.model;

import com.agendamentos.online.modules.model.Interface.Pessoa;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "profissional_tb")
@EqualsAndHashCode(callSuper = true)
public class Profissional extends Pessoa {
    
    @Column(unique = true, name = "code_col")
    private String code;

    @OneToOne
    @JoinColumn(name = "profissional_id")
    private Agendamento agendamento;

    @JsonBackReference
    @ManyToOne
    private Clinica clinica;

}
