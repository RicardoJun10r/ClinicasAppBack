package com.agendamentos.online.modules.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamentos.online.modules.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

}
