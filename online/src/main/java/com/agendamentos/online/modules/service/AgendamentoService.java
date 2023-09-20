package com.agendamentos.online.modules.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.repository.AgendamentoRepository;
import com.agendamentos.online.util.enums.ApointmentEnum;

@Service
public class AgendamentoService {
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento save(Agendamento agendamento){
        
        agendamento.setStatus(ApointmentEnum.MARCADO);
        
        return this.agendamentoRepository.save(agendamento);
        
    }

    public List<Agendamento> findAll(){
        return this.agendamentoRepository.findAll();
    }

    public Optional<Agendamento> find(UUID uuid){
        Optional<Agendamento> aOptional = this.agendamentoRepository.findById(uuid);

        if(aOptional.isPresent()){
            return aOptional;
        }

        throw new ResourceNotFoundException("Agendamento não encontrado!");
    }

    public Agendamento update(Agendamento novo, UUID velho){
        Optional<Agendamento> aOptional = this.agendamentoRepository.findById(velho);

        if(aOptional.isPresent()){
            attCampos(aOptional.get(), novo);
            return this.agendamentoRepository.save(aOptional.get());
        }

        throw new ResourceNotFoundException("Agendamento não encontrado!");

    }

    public String delete(UUID uuid){
        Optional<Agendamento> aOptional = this.agendamentoRepository.findById(uuid);

        if(aOptional.isPresent()){
            this.agendamentoRepository.deleteById(uuid);
            return "Deletado [ " + uuid + " ]";
        }

        throw new ResourceNotFoundException("Agendamento não encontrado!");
    }

    private void attCampos(Agendamento velho, Agendamento novo){
        
        if(novo.getStatus() != null){
            velho.setStatus(novo.getStatus());
        }

        if(novo.getAppointmentDate() != null){
            velho.setAppointmentDate(novo.getAppointmentDate());
        }

        if(novo.getAppointmentTime() != null || novo.getAppointmentTime() > 0.0){
            velho.setAppointmentTime(novo.getAppointmentTime());
        }

    }

}
