package com.agendamentos.online.modules.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.ProfissionalRepository;

@Service
public class ProfissionalService {
    
    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Profissional save(Profissional profissional){

        if(this.profissionalRepository.findByCode(profissional.getCode()).get() == null){
            this.profissionalRepository.save(profissional);
        }

        throw new ResourceBadRequest("Você já possui cadastro!");

    }

    public Profissional find(String code){
        Optional<Profissional> paciente = this.profissionalRepository.findByCode(code);
        if(paciente.isPresent()){
            return paciente.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public Profissional update(UUID uuid, Profissional profissionalAtt){
        Optional<Profissional> proficional = this.profissionalRepository.findById(uuid);
        if(proficional.isPresent()){
            this.attCampos(proficional.get(), profissionalAtt);
            this.profissionalRepository.save(proficional.get());
            return proficional.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public String delete(UUID uuid){
        Optional<Profissional> proficional = this.profissionalRepository.findById(uuid);
        if(proficional.isPresent()){
            
            this.profissionalRepository.deleteById(proficional.get().getUuid());
            return "Deletado [ " + proficional.get().getName() + " ]";
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    private void attCampos(Profissional velho, Profissional novo){
        
        if(novo.getName() != null && !novo.getName().isEmpty()){
            velho.setName(novo.getName());
        }

        if(novo.getCode() != null && !novo.getCode().isEmpty()){
            velho.setCode(novo.getCode());
        }

    }

}
