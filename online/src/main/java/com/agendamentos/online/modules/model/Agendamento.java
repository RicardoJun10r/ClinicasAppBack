package com.agendamentos.online.modules.model;

import java.time.LocalDate;
import java.util.UUID;

import com.agendamentos.online.util.enums.ApointmentEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agendamentos_tb")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID uuid;

    @Column(name = "date_col")
    private LocalDate appointmentDate;

    @Column(name = "time_col")
    private Double appointmentTime;

    @Column(name = "state_col")
    private ApointmentEnum status;

    @Column(name = "duration")
    private Integer sessionTime;

    @JsonBackReference(value = "clinica-agendamento")
    @ManyToOne
    private Clinica clinica;

    @JsonBackReference(value = "paciente-agendamento")
    @ManyToOne
    private Paciente paciente;

    @JsonBackReference(value = "profissional-agendamento")
    @ManyToOne
    private Profissional profissional;

}
