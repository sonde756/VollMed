package com.vollmed.vollmed.dto.consulta;

import com.vollmed.vollmed.model.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsultaDTO(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsultaDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
