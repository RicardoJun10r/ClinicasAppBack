package com.agendamentos.online.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.service.PacienteService;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> create(@RequestBody Paciente paciente){
        return new ResponseEntity<Paciente>(this.pacienteService.save(paciente), HttpStatus.CREATED);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> find(@RequestBody String cpf){
        return new ResponseEntity<Paciente>(this.pacienteService.find(cpf).get(), HttpStatus.OK);
    }

}