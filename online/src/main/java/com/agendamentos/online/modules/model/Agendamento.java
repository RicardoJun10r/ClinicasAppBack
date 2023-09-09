package com.agendamentos.online.modules.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.agendamentos.online.util.enums.ApointmentEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agendamentos_tb")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID uuid;

    @Column(name = "date_col")
    private LocalDateTime consulta;

    @Column(name = "state_col")
    private ApointmentEnum apointmentEnum;

    @JsonBackReference
    @ManyToOne
    private Clinica clinica;

    @OneToOne(targetEntity = Paciente.class, mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Paciente pacientes;

    @OneToOne(targetEntity = Profissional.class, mappedBy = "agendamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profissional profissionais;

}
