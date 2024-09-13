package me.dio.spring_pet_api.controller;

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
    public ResponseEntity<List<Agendamento>> listarTodos() {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody Agendamento agendamento) {
        Agendamento agendamentoSalvo = agendamentoService.salvar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(Long id, Agendamento agendamentoAtualizado) {
        Agendamento agendamento = agendamentoService.atualizar(id, agendamentoAtualizado);

        if (agendamento.getId() != null) {
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
