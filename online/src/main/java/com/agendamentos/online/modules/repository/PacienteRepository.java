package com.agendamentos.online.modules.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamentos.online.modules.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    Optional<Paciente> findByCpf(String cpf);
}
