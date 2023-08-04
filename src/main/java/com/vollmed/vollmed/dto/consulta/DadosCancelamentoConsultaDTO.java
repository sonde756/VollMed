package com.vollmed.vollmed.dto.consulta;

import com.vollmed.vollmed.enumeration.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsultaDTO(

        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
) {
}
