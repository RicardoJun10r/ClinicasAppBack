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
import com.agendamentos.online.modules.model.Clinica;
import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.ClinicaRepository;

@Service
public class ClinicaService {
    
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private AgendamentoService agendamentoService;

    public Clinica save(Clinica clinica){

        Optional<Clinica> cOptional = this.clinicaRepository.findByCnpj(clinica.getCnpj());

        if(cOptional.isPresent()){
            throw new ResourceBadRequest("Você já possui cadastro!");
        }
        
        return this.clinicaRepository.save(clinica);

    }

    public Optional<Clinica> find(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica;
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public Clinica findByLogin(String login){
        Optional<Clinica> pOptional = this.clinicaRepository.findByLogin(login);
        if(pOptional.isPresent()) return pOptional.get();
        throw new ResourceNotFoundException("Não encontrado!");
    }

    public Clinica update(UUID uuid, Clinica clinicaAtt){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            this.attCampos(clinica.get(), clinicaAtt);
            this.clinicaRepository.save(clinica.get());
            return clinica.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public String delete(UUID uuid){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            
            this.clinicaRepository.deleteById(clinica.get().getUuid());
            return "Deletado [ " + clinica.get().getName() + " ]";
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public Profissional addWorker(Profissional profissional, String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            
            profissional.setClinica(clinica.get());
            Profissional criado = this.profissionalService.save(profissional);
            clinica.get().getProfissionais().add(criado);
            this.clinicaRepository.save(clinica.get());
            return criado;
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public List<Profissional> listWorkers(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica.get().getProfissionais();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    public Agendamento addAppointment(Agendamento agendamento, String code, String cpf, UUID uuid){

        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            Optional<Profissional> profissional = findWorker(clinica.get().getProfissionais(), code);
            Optional<Paciente> paciente = this.pacienteService.find(cpf);
            if(profissional.isPresent()){
                
                clinica.get().getAgendamentos().add(agendamento);

                agendamento.setClinica(clinica.get());

                agendamento.setProfissional(profissional.get());

                agendamento.setPaciente(paciente.get());

                Optional<Agendamento> criado = Optional.of(this.agendamentoService.save(agendamento));

                this.pacienteService.addAppointment(criado.get());

                this.profissionalService.addAppointment(criado.get());

                this.clinicaRepository.save(clinica.get());

                return criado.get();
            }

            throw new ResourceNotFoundException("Profissional não encontrada!");

        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
        
    }

    public List<Agendamento> listAppointments(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica.get().getAgendamentos();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
    }

    private Optional<Profissional> findWorker(List<Profissional> lista, String code){
        Iterator<Profissional> iterator = lista.iterator();
        
        while(iterator.hasNext()){
            Profissional index = iterator.next();
            if(index.getCode().equals(code)) return Optional.of(index);
        }

        return null;
    }

    private void attCampos(Clinica velho, Clinica novo){
        
        if(novo.getName() != null && !novo.getName().isEmpty()){
            velho.setName(novo.getName());
        }

        if(novo.getCnpj() != null && !novo.getCnpj().isEmpty()){
            velho.setCnpj(novo.getCnpj());
        }

        if(novo.getAddress() != null && !novo.getAddress().isEmpty()){
            velho.setAddress(novo.getAddress());
        }

    }

}
