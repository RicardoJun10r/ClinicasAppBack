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
import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.repository.PacienteRepository;
import com.agendamentos.online.util.enums.ApointmentEnum;

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente save(Paciente paciente){

        Optional<Paciente> pOptional = this.pacienteRepository.findByCpf(paciente.getCpf());

        if(pOptional.isPresent()){
            throw new ResourceBadRequest("Você já possui cadastro!");
        }
        
        return this.pacienteRepository.save(paciente);

    }

    public Paciente addAppointment(Agendamento agendamento){
        Optional<Paciente> pOptional = this.pacienteRepository.findById(agendamento.getPaciente().getUuid());

        if(pOptional.isPresent()){

            if(pOptional.get().getAgendamentos() == null || agendamento.getStatus() != ApointmentEnum.MARCADO){
                pOptional.get().getAgendamentos().add(agendamento);
                return this.pacienteRepository.save(pOptional.get());
            }
            throw new ResourceBadRequest("Horário indisponível !");
        }
        throw new ResourceNotFoundException("Profissional não encontrado !");
    }

    public Paciente findByLogin(String login){
        Optional<Paciente> pOptional = this.pacienteRepository.findByLogin(login);
        if(pOptional.isPresent()) return pOptional.get();
        throw new ResourceNotFoundException("Não encontrado!");
    }

    public Agendamento findAppointment(List<Agendamento> lista, UUID id){
        
        Iterator<Agendamento> iterator = lista.iterator();

        while (iterator.hasNext()) {
            Agendamento index = iterator.next();
            if(index.getUuid().equals(id)) return index;
        }

        return null;

    }

    public Optional<Paciente> find(String cpf){
        Optional<Paciente> paciente = this.pacienteRepository.findByCpf(cpf);
        if(paciente.isPresent()){
            return paciente;
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
