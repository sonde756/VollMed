package com.vollmed.vollmed.dto.medico;

import com.vollmed.vollmed.enumeration.Especialidade;
import com.vollmed.vollmed.model.Medico;

import java.util.Optional;

public record DadosListagemMedicoDTO(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListagemMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
