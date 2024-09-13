package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.model.Servico;
import me.dio.spring_pet_api.domain.model.Veterinario;
import me.dio.spring_pet_api.service.ServicoService;
import me.dio.spring_pet_api.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinario")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public ResponseEntity<List<Veterinario>> listarTodos() {
        List<Veterinario> veterinarios = veterinarioService.listarTodos();
        return ResponseEntity.ok(veterinarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscarPorId(@PathVariable Long id) {
        Veterinario veterinario = veterinarioService.buscarPorId(id);
        return ResponseEntity.ok(veterinario);
    }

    @PostMapping
    public ResponseEntity<Veterinario> salvar(@RequestBody Veterinario veterinario) {
        Veterinario veterinarioSalvo = veterinarioService.salvar(veterinario);

        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioSalvo);
    }

    @PutMapping
    public ResponseEntity<Veterinario> atualizar(Long id, Veterinario veterinarioAtualizado) {
        Veterinario veterinario = veterinarioService.atualizar(id, veterinarioAtualizado);

        if (veterinario.getId() != null) {
            return ResponseEntity.ok(veterinario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
