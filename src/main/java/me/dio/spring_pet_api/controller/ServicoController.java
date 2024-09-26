package me.dio.spring_pet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.spring_pet_api.domain.dto.ServicoDTO;
import me.dio.spring_pet_api.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
@Tag(name = "Serviço", description = "Endpoint para gerenciamento de serviços")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os serviços")
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        ServicoDTO servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @PostMapping
    @Operation(summary = "Criar novo serviço")
    public ResponseEntity<ServicoDTO> salvar(@RequestBody ServicoDTO servico) {
        ServicoDTO servicoSalvo = servicoService.salvar(servico);

        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    @PutMapping
    @Operation(summary = "Atualizar serviço existente")
    public ResponseEntity<ServicoDTO> atualizar(Long id, ServicoDTO servicoAtualizado) {
        ServicoDTO servico = servicoService.atualizar(id, servicoAtualizado);

        if (servico.id() != null) {
            return ResponseEntity.ok(servico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço existente por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}
