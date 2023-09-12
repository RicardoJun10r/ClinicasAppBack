package com.agendamentos.online.modules.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clinica_tb")
public class Clinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID uuid;

    @Column(name = "name_col")
    private String name;

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
