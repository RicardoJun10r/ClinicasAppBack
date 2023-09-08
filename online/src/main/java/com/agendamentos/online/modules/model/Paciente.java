package com.agendamentos.online.modules.model;

import com.agendamentos.online.modules.model.Interface.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    
    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Agendamento agendamento;

}
