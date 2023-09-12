package com.agendamentos.online.modules.model;

import java.util.List;

import com.agendamentos.online.modules.model.Interface.Pessoa;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "paciente_tb")
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Pessoa {

    @Column(unique = true, name = "cpf_col")
    private String cpf;
    
    @JsonManagedReference
    @OneToMany(targetEntity = Agendamento.class, mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

}
