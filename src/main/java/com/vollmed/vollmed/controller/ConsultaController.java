package com.vollmed.vollmed.controller;

import com.vollmed.vollmed.dto.consulta.DadosAgendamentoConsultaDTO;
import com.vollmed.vollmed.dto.consulta.DadosCancelamentoConsultaDTO;
import com.vollmed.vollmed.services.AgendaDeConsultas;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final AgendaDeConsultas agenda;

    @Autowired
    public ConsultaController(AgendaDeConsultas agenda) {
        this.agenda = agenda;
    }


    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsultaDTO dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsultaDTO dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
