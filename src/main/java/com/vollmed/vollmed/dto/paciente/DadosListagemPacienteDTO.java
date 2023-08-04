package com.vollmed.vollmed.dto.paciente;

import com.vollmed.vollmed.dto.DadosEnderecoDTO;
import com.vollmed.vollmed.model.Paciente;

public record DadosListagemPacienteDTO(String nome, String email, String cpf) {

    public DadosListagemPacienteDTO(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
