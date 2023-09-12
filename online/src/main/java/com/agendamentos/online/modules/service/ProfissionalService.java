package com.agendamentos.online.modules.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.ProfissionalRepository;
import com.agendamentos.online.util.enums.ApointmentEnum;

@Service
public class ProfissionalService {
    
    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Profissional save(Profissional profissional){

        Optional<Profissional> prOptional = this.profissionalRepository.findByCode(profissional.getCode());

        if(prOptional.isPresent()){
            throw new ResourceBadRequest("Você já possui cadastro!");
        }
        
        return this.profissionalRepository.save(profissional);

    }

    public Profissional addAppointment(Agendamento agendamento){
        Optional<Profissional> pOptional = this.profissionalRepository.findById(agendamento.getProfissional().getUuid());

        if(pOptional.isPresent()){

            if(pOptional.get().getAgendamentos() == null || agendamento.getApointmentEnum() != ApointmentEnum.MARCADO){
                pOptional.get().getAgendamentos().add(agendamento);
                return this.profissionalRepository.save(pOptional.get());
            }
            throw new ResourceBadRequest("Horário indisponível !");
        }
        throw new ResourceNotFoundException("Profissional não encontrado !");
    }

    public Agendamento findAppointment(List<Agendamento> lista, UUID id){
        
        Iterator<Agendamento> iterator = lista.iterator();

        while (iterator.hasNext()) {
            Agendamento index = iterator.next();
            if(index.getUuid().equals(id)) return index;
        }

        return null;

    }

    public Optional<Profissional> find(String code){
        Optional<Profissional> paciente = this.profissionalRepository.findByCode(code);
        if(paciente.isPresent()){
            return paciente;
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
