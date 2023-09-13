package com.agendamentos.online.modules.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceConditionFailed;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.AgendamentoRepository;
import com.agendamentos.online.util.enums.ApointmentEnum;

@Service
public class AgendamentoService {
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfissionalService profissionalService;

    public Agendamento save(Agendamento agendamento){
        // Optional<Agendamento> aOptional = this.agendamentoRepository.findByConsulta(agendamento.getConsulta());
        Optional<Agendamento> aOptional = this.agendamentoRepository.findAll().stream().filter(index -> index.getConsulta().equals(agendamento.getConsulta())).findFirst();
        if(aOptional.isPresent()){
            if(aOptional.get().getApointmentEnum() == ApointmentEnum.MARCADO){
                throw new ResourceConditionFailed("Horário indisponível");
            }
            // return update(agendamento, aOptional.get().getUuid());
        }

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
        
        if(novo.getApointmentEnum() != null){
            velho.setApointmentEnum(novo.getApointmentEnum());
        }

        if(novo.getConsulta() != null){
            velho.setConsulta(novo.getConsulta());
        }

    }

}
