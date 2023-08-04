package com.vollmed.vollmed.dto.paciente;

import com.vollmed.vollmed.dto.DadosEnderecoDTO;

public record DadosAtualizacaoPacienteDTO(String nome, String telefone, DadosEnderecoDTO endereco) {

}
