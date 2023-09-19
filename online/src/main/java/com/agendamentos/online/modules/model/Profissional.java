package com.agendamentos.online.modules.model;

import java.util.List;

import com.agendamentos.online.modules.model.Interface.Pessoa;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(name = "especialidade_col")
    private String especialidade;

    @JsonManagedReference(value = "profissional-agendamento")
    @OneToMany(targetEntity = Agendamento.class, mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    @JsonBackReference(value = "clinica-profissional")
    @ManyToOne
    private Clinica clinica;

}
