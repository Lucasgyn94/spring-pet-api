package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.repository.ServicoRepository;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        List<Servico> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        Servico servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @PostMapping
    public ResponseEntity<Servico> salvar(@RequestBody Servico servico) {
        Servico servicoSalvo = servicoService.salvar(servico);

        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    @PutMapping
    public ResponseEntity<Servico> atualizar(Long id, Servico servicoAtualizado) {
        Servico servico = servicoService.atualizar(id, servicoAtualizado);

        if (servico.getId() != null) {
            return ResponseEntity.ok(servico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}
