package com.agendamentos.online.modules.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamentos.online.modules.model.Agendamento;
import java.util.Optional;
import java.time.LocalDateTime;


public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    Optional<Agendamento> findByConsulta(LocalDateTime consulta);
}
