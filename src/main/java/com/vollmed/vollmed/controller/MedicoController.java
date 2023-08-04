package com.vollmed.vollmed.controller;


import com.vollmed.vollmed.dto.medico.CadastroMedicoDTO;
import com.vollmed.vollmed.dto.medico.DadosAtualizacaoMedicoDTO;
import com.vollmed.vollmed.dto.medico.DadosListagemMedicoDTO;
import com.vollmed.vollmed.model.Medico;
import com.vollmed.vollmed.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicoRepository repository;

    @Autowired
    public MedicoController(MedicoRepository repository) {
        this.repository = repository;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Medico> cadastro(@RequestBody @Valid CadastroMedicoDTO dados) {
        var medico = new Medico(dados);
        repository.save(medico);
        return ResponseEntity.ok(medico);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicoDTO>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicoDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemMedicoDTO> porId(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok().body(new DadosListagemMedicoDTO(medico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemMedicoDTO> update(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoMedicoDTO dados) {
        var medico = repository.getReferenceById(id);

        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosListagemMedicoDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        var medico = repository.findById(id);
        medico.orElseThrow(() -> new RuntimeException("Não foi possivel desativar")).enativar();
        return ResponseEntity.noContent().build();
    }


}
