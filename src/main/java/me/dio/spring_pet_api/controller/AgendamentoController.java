package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.dto.AgendamentoDTO;
import me.dio.spring_pet_api.domain.model.Agendamento;
import me.dio.spring_pet_api.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping()
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<AgendamentoDTO> salvar(@RequestBody AgendamentoDTO agendamento) {
        AgendamentoDTO agendamentoSalvo = agendamentoService.salvar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizar(Long id, AgendamentoDTO agendamentoAtualizado) {
        AgendamentoDTO agendamento = agendamentoService.atualizar(id, agendamentoAtualizado);

        if (agendamento.id() != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }





}
