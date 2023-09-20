package com.agendamentos.online.shared;

import java.time.LocalDate;
import java.util.UUID;

import com.agendamentos.online.util.enums.ApointmentEnum;

import lombok.Data;

@Data
public class AgendamentoResponse{
    private UUID uuid;
    private LocalDate appointmentDate;
    private Double appointmentTime;
    private ApointmentEnum status;
    private Integer sessionTime;
    private PacienteResponse paciente;
    private ProfissionalDTO profissional;
}
