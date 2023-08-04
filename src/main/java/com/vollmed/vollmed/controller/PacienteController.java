package com.vollmed.vollmed.controller;


import com.vollmed.vollmed.dto.paciente.CadastroPacienteDTO;
import com.vollmed.vollmed.dto.paciente.DadosAtualizacaoPacienteDTO;
import com.vollmed.vollmed.dto.paciente.DadosListagemPacienteDTO;
import com.vollmed.vollmed.model.Paciente;
import com.vollmed.vollmed.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteRepository repository;

    @Autowired
    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Paciente> cadastro(@RequestBody @Valid CadastroPacienteDTO dados) {
        var paciente = new Paciente(dados);

        repository.save(paciente);

        return ResponseEntity.ok(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacienteDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemPacienteDTO> porId(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok().body(new DadosListagemPacienteDTO(paciente));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemPacienteDTO> update(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoPacienteDTO dados) {
        var paciente = repository.getReferenceById(id);
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemPacienteDTO(paciente));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Paciente> excluir(@PathVariable Long id) {
        var paciente = repository.findById(id);
        paciente.orElseThrow(() -> new RuntimeException("Não foi possivel desativar")).enativar();
        return ResponseEntity.noContent().build();
    }

}
