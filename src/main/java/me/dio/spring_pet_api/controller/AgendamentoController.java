package me.dio.spring_pet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agendamento")
@Tag(name = "Agendamento", description = "Endpoints para gerenciamento de agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Operation(summary = "Lista todos os agendamentos")
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @Operation(summary = "Busca um agendamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @Operation(summary = "Cria um novo agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<AgendamentoDTO> salvar(@Valid @RequestBody AgendamentoDTO agendamento) {
        AgendamentoDTO agendamentoSalvo = agendamentoService.salvar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @Operation(summary = "Atualiza um agendamento existente")
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoDTO agendamentoAtualizado) {
        AgendamentoDTO agendamento = agendamentoService.atualizar(id, agendamentoAtualizado);
        return ResponseEntity.ok(agendamento);
    }

    @Operation(summary = "Deleta um agendamento por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
