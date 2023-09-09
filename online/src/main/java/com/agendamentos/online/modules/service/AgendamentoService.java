package com.agendamentos.online.modules.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceConditionFailed;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.repository.AgendamentoRepository;
import com.agendamentos.online.util.enums.ApointmentEnum;

@Service
public class AgendamentoService {
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento save(Agendamento agendamento){
        Optional<Agendamento> aOptional = this.agendamentoRepository.findByConsulta(agendamento.getConsulta());
        if(aOptional.isPresent()){
            if(aOptional.get().getApointmentEnum() == ApointmentEnum.MARCADO){
                throw new ResourceConditionFailed("Horário indisponível");
            }
            
        }

        return this.agendamentoRepository.save(agendamento);
    }

}
