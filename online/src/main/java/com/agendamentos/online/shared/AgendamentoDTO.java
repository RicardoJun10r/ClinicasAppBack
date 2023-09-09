package com.agendamentos.online.shared;

import com.agendamentos.online.modules.model.Agendamento;

public record AgendamentoDTO(String code, String cpf, Agendamento agendamento) {
    
}
