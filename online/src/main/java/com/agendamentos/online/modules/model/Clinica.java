package com.agendamentos.online.modules.model;

import java.util.List;

import com.agendamentos.online.modules.model.Interface.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "clinica_tb")
public class Clinica extends Usuario {

    @Column(name = "cnpj_col")
    private String cnpj;

    @Column(name = "address_col")
    private String address;

    @JsonManagedReference(value = "clinica-agendamento")
    @OneToMany(targetEntity = Agendamento.class, mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    @JsonManagedReference(value = "clinica-profissional")
    @OneToMany(targetEntity = Profissional.class, mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Profissional> profissionais;

}
