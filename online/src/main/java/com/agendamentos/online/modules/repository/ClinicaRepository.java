package com.agendamentos.online.modules.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendamentos.online.modules.model.Clinica;
import java.util.Optional;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, UUID> {
    Optional<Clinica> findByCnpj(String cnpj);
    Optional<Clinica> findByLogin(String login);
}
