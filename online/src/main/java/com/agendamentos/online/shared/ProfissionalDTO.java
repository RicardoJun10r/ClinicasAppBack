package com.agendamentos.online.shared;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ProfissionalDTO {
    private UUID uuid;
    private String name;
    private LocalDate createdAt;
    private String imageURL;
    private String code;
    private String especialidade;
}
