package com.agendamentos.online.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.model.Clinica;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.service.ClinicaService;
import com.agendamentos.online.shared.AgendamentoDTO;

@RestController
@RequestMapping("/api/clinica")
public class ClinicaController {
    
    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<Clinica> create(@RequestBody Clinica clinica){
        return new ResponseEntity<Clinica>(this.clinicaService.save(clinica), HttpStatus.CREATED);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Clinica> find(@PathVariable String cnpj){
        return new ResponseEntity<Clinica>(this.clinicaService.find(cnpj).get(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Clinica>> findAll(){
        return new ResponseEntity<List<Clinica>>(this.clinicaService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Clinica> findByLogin(@RequestParam String login){
        return new ResponseEntity<Clinica>(this.clinicaService.findByLogin(login), HttpStatus.OK);
    }

    @PostMapping("/{cnpj}/profissional")
    public ResponseEntity<Profissional> addWorker(@RequestBody Profissional profissional, @PathVariable String cnpj){
        return new ResponseEntity<Profissional>(this.clinicaService.addWorker(profissional, cnpj), HttpStatus.CREATED);
    }

    @PostMapping("/{uuid}/agendamentos")
    public ResponseEntity<Agendamento> addAppointment(@RequestBody AgendamentoDTO agendamentoDTO, @PathVariable UUID uuid){
        return new ResponseEntity<Agendamento>(this.clinicaService.addAppointment(agendamentoDTO.agendamento(), agendamentoDTO.code(), agendamentoDTO.cpf(), uuid), HttpStatus.CREATED);
    }

    @DeleteMapping("/uuid")
    public ResponseEntity<String> delete(@PathVariable UUID uuid){
        return new ResponseEntity<String>(this.clinicaService.delete(uuid), HttpStatus.OK);
    }

}
