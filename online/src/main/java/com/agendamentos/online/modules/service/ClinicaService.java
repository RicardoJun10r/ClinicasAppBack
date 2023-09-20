package com.agendamentos.online.modules.service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceConditionFailed;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;
import com.agendamentos.online.modules.model.Agendamento;
import com.agendamentos.online.modules.model.Clinica;
import com.agendamentos.online.modules.model.Paciente;
import com.agendamentos.online.modules.model.Profissional;
import com.agendamentos.online.modules.repository.ClinicaRepository;
import com.agendamentos.online.shared.AgendamentoResponse;
import com.agendamentos.online.shared.ClinicaResponse;

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

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public List<Clinica> findAll(){
        List<Clinica> clinicas = this.clinicaRepository.findAll();
        if(!clinicas.isEmpty()){
            return clinicas;
        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public Clinica findByLogin(String login){
        Optional<Clinica> pOptional = this.clinicaRepository.findByLogin(login);
        if(pOptional.isPresent()) return pOptional.get();
        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public Clinica update(UUID uuid, Clinica clinicaAtt){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            this.attCampos(clinica.get(), clinicaAtt);
            this.clinicaRepository.save(clinica.get());
            return clinica.get();
        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public Profissional updateProfissional(UUID uuid, Profissional old){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){

            Optional<Profissional> aux = clinica.get().getProfissionais().stream().filter(pro -> pro.getCode().equals(old.getCode())).findFirst();

            if(aux.isPresent()){
                return this.profissionalService.update(aux.get().getCode(), aux.get());
            }

            throw new ResourceNotFoundException("Profissional não encontrado!");

        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public Agendamento updateAgendamento(UUID clinicaid, UUID old, Agendamento novo){
        Optional<Clinica> clinica = this.clinicaRepository.findById(clinicaid);
        if(clinica.isPresent()){

            Optional<Agendamento> aux = clinica.get().getAgendamentos().stream().filter(pro -> pro.getUuid().equals(old)).findFirst();

            if(aux.isPresent()){
                return this.agendamentoService.update(novo, old);
            }

            throw new ResourceNotFoundException("Agendamento não encontrado!");

        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public String delete(UUID uuid){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            
            this.clinicaRepository.deleteById(clinica.get().getUuid());
            return "Deletado [ " + clinica.get().getName() + " ]";
        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public String deleteWorker(UUID uuid, String code){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){

            Optional<Profissional> aux = clinica.get().getProfissionais().stream().filter(p -> p.getCode().equals(code)).findFirst();

            clinica.get().getProfissionais().removeIf(p -> p.getCode().equals(code));
            
            if(aux.isPresent()){
                this.clinicaRepository.save(clinica.get());
                return this.profissionalService.delete(aux.get().getUuid());
            }

            throw new ResourceNotFoundException("Profissional não encontrado!");

        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
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

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public List<Profissional> listWorkers(String cnpj){
        Optional<Clinica> clinica = this.clinicaRepository.findByCnpj(cnpj);
        if(clinica.isPresent()){
            return clinica.get().getProfissionais();
        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
    }

    public Agendamento addAppointment(Agendamento agendamento, String code, String cpf, UUID uuid){

        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            Optional<Profissional> profissional = findWorker(clinica.get().getProfissionais(), code);
            Optional<Paciente> paciente = this.pacienteService.find(cpf);
            if(profissional.isPresent()){

                 Optional<Agendamento> aux = profissional.get().getAgendamentos().stream().filter(a -> a.getAppointmentDate().equals(agendamento.getAppointmentDate()) && a.getAppointmentTime().equals(agendamento.getAppointmentTime())).findFirst();
                
                if(aux.isPresent()){
                    throw new ResourceConditionFailed("Horário indisponível!");
                }

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

            throw new ResourceNotFoundException("Profissional não encontrado!");

        }

        throw new ResourceNotFoundException("Clínica não encontrada!");
        
    }

    public List<AgendamentoResponse> getAppointments(UUID uuid, String code, LocalDate dia){
        Optional<Clinica> clinica = this.clinicaRepository.findById(uuid);
        if(clinica.isPresent()){
            Optional<Profissional> profissional = findWorker(clinica.get().getProfissionais(), code);
            if(profissional.isPresent()){
                ModelMapper mapper = new ModelMapper();
                List<Agendamento> agendamentos = profissional.get().getAgendamentos().stream().filter(agendamento -> agendamento.getAppointmentDate().equals(dia)).sorted((agendamento1, agendamento2) -> agendamento1.getAppointmentDate().compareTo(agendamento2.getAppointmentDate())).collect(Collectors.toList());
                List<AgendamentoResponse> aux = agendamentos.stream().map(
                    a -> mapper.map(a, AgendamentoResponse.class)
                ).collect(Collectors.toList());
                return aux;
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

        if(novo.getLogin() != null && !novo.getLogin().isEmpty()){
            velho.setLogin(novo.getLogin());
        }

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
