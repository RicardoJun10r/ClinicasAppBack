package com.agendamentos.online.modules.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.model.Clinica;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.ClinicaRepository;

@Service
public class ClinicaService {
    
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ProfissionalService profissionalService;

    public Clinica save(Clinica clinica){

        if(this.clinicaRepository.findByCnpj(clinica.getCnpj()).get() == null){
            this.clinicaRepository.save(clinica);
        }

        throw new ResourceBadRequest("Você já possui cadastro!");

    }

    public Clinica find(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica.get();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
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

    public String addWorker(Profissional profissional, String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            
            profissional.setClinica(clinica.get());
            Profissional criado = this.profissionalService.save(profissional);
            clinica.get().getProfissionais().add(criado);
            this.clinicaRepository.save(clinica.get());
            return "Adicionado !";
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

    public List<Agendamento> listAppointments(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica.get().getAgendamentos();
        }

        throw new ResourceNotFoundException("Conta não encontrada!");
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
