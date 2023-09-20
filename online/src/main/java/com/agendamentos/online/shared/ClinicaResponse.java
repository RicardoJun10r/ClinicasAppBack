package com.agendamentos.online.shared;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.agendamentos.online.modules.model.Profissional;

public record ClinicaResponse(
    UUID uuid,
    String name,
    LocalDate createdAt,
    String imageURL,
    String login,
    String password,
    Boolean cargo,
    String cnpj,
    String address,
    List<AgendamentoResponse> agendamentos,
    List<Profissional> profissionais
) {
    
}
