package com.agendamentos.online.modules.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamentos.online.modules.model.Profissional;
import java.util.Optional;


public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
    Optional<Profissional> findByCode(String code);
}
