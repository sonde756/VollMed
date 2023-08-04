package com.vollmed.vollmed.services.validation;

import com.vollmed.vollmed.exception.ValidacaoException;
import com.vollmed.vollmed.dto.consulta.DadosAgendamentoConsultaDTO;
import com.vollmed.vollmed.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository repository;

    @Autowired
    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsultaDTO dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario)
            throw new ValidacaoException("Medico já possui outra consulta agendada nesse mesmo horario");
    }
}
