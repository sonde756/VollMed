package com.vollmed.vollmed.services;

import com.vollmed.vollmed.ValidacaoException;
import com.vollmed.vollmed.dto.consulta.DadosAgendamentoConsultaDTO;
import com.vollmed.vollmed.dto.consulta.DadosCancelamentoConsultaDTO;
import com.vollmed.vollmed.dto.consulta.DadosDetalhamentoConsultaDTO;
import com.vollmed.vollmed.model.Consulta;
import com.vollmed.vollmed.model.Medico;
import com.vollmed.vollmed.repository.ConsultaRepository;
import com.vollmed.vollmed.repository.MedicoRepository;
import com.vollmed.vollmed.repository.PacienteRepository;
import com.vollmed.vollmed.services.validation.ValidadorAgendamentoDeConsulta;
import com.vollmed.vollmed.services.validation.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoDeConsulta> validadores;

    private final List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    @Autowired
    public AgendaDeConsultas(PacienteRepository pacienteRepository, MedicoRepository medicoRepository, ConsultaRepository consultaRepository, List<ValidadorAgendamentoDeConsulta> validadores, List<ValidadorCancelamentoDeConsulta> validadoresCancelamento) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.validadores = validadores;
        this.validadoresCancelamento = validadoresCancelamento;
    }




    public DadosDetalhamentoConsultaDTO agendar(DadosAgendamentoConsultaDTO dados) {

        if (!pacienteRepository.existsById(dados.idPaciente()))
            throw new ValidacaoException("Id do paciente não existe");

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
            throw new ValidacaoException("Id do medico não existe");

        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        if (medico == null){
            throw new ValidacaoException("Não existe medico disponivel nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsultaDTO(consulta);
    }


    public void cancelar(DadosCancelamentoConsultaDTO dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }


    private Medico escolherMedico(DadosAgendamentoConsultaDTO dados) {
        if (dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());


        if (dados.especialidade() == null)
            throw new ValidacaoException("Especialidade obrigatoria");


        return medicoRepository.escolherMedicoAleatoriaLivreNaData(dados.especialidade(), dados.data());
    }
}
