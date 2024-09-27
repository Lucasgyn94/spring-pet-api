package me.dio.spring_pet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.spring_pet_api.domain.dto.VeterinarioDTO;
import me.dio.spring_pet_api.service.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinario")
@Tag(name = "Veterinario", description = "Endpoint para gerenciamento de veterinarios")
@SecurityRequirement(name = "bearer-key")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os veterinarios")
    public ResponseEntity<List<VeterinarioDTO>> listarTodos() {
        List<VeterinarioDTO> veterinarios = veterinarioService.listarTodos();
        return ResponseEntity.ok(veterinarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinarios por ID")
    public ResponseEntity<VeterinarioDTO> buscarPorId(@PathVariable Long id) {
        VeterinarioDTO veterinario = veterinarioService.buscarPorId(id);
        return ResponseEntity.ok(veterinario);
    }

    @PostMapping
    @Operation(summary = "Criar novo veterinario")
    public ResponseEntity<VeterinarioDTO> salvar(@RequestBody VeterinarioDTO veterinario) {
        VeterinarioDTO veterinarioSalvo = veterinarioService.salvar(veterinario);

        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioSalvo);
    }

    @PutMapping
    @Operation(summary = "Atualizar veterinario existente")
    public ResponseEntity<VeterinarioDTO> atualizar(Long id, VeterinarioDTO veterinarioDTOAtualizado) {
        VeterinarioDTO veterinario = veterinarioService.atualizar(id, veterinarioDTOAtualizado);

        if (veterinario.id() != null) {
            return ResponseEntity.ok(veterinario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veterinario existente por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
