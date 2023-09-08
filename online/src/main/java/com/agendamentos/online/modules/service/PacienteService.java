package com.agendamentos.online.modules.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.repository.PacienteRepository;

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente save(Paciente paciente){

        if(this.pacienteRepository.findByCpf(paciente.getCpf()).get() == null){
            this.pacienteRepository.save(paciente);
        }

        throw new ResourceBadRequest("Você já possui cadastro!");

    }

    public Paciente find(String cpf){
        Optional<Paciente> paciente = this.pacienteRepository.findByCpf(cpf);
        if(paciente.isPresent()){
            return paciente.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public Paciente update(UUID uuid, Paciente pacienteAtt){
        Optional<Paciente> paciente = this.pacienteRepository.findById(uuid);
        if(paciente.isPresent()){
            this.attCampos(paciente.get(), pacienteAtt);
            this.pacienteRepository.save(paciente.get());
            return paciente.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public String delete(UUID uuid){
        Optional<Paciente> paciente = this.pacienteRepository.findById(uuid);
        if(paciente.isPresent()){
            
            this.pacienteRepository.deleteById(paciente.get().getUuid());
            return "Deletado [ " + paciente.get().getName() + " ]";
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    private void attCampos(Paciente velho, Paciente novo){
        
        if(novo.getName() != null && !novo.getName().isEmpty()){
            velho.setName(novo.getName());
        }

        if(novo.getCpf() != null && !novo.getCpf().isEmpty()){
            velho.setCpf(novo.getCpf());
        }

    }

}
