package com.vollmed.vollmed.services.validation;

import com.vollmed.vollmed.ValidacaoException;
import com.vollmed.vollmed.dto.consulta.DadosAgendamentoConsultaDTO;
import com.vollmed.vollmed.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    private final MedicoRepository repository;

    @Autowired
    public ValidadorMedicoAtivo(MedicoRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsultaDTO dados){
        if (dados.idMedico() == null){
            return;
        }

        var medicoAtivo = repository.findByAtivoTrue(dados.idMedico());
        if (!medicoAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com medico excluido");
        }
    }
}
