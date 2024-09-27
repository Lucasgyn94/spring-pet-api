package me.dio.spring_pet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@Tag(name = "Pet", description = "Endpoint para gerenciamento de pets")
@SecurityRequirement(name = "bearer-key")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    @Operation(summary = "Listar todos os Pets")
    public ResponseEntity<List<PetDTO>> listarTodos() {
        List<PetDTO> pets = petService.listarTodos();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetDTO> buscarPorId(@PathVariable Long id) {
        PetDTO pet = petService.buscarPorId(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    @Operation(summary = "Criar novo pet")
    public ResponseEntity<PetDTO> salvar(@RequestBody PetDTO pet) {
        if (pet.clienteId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        PetDTO petSalvo = petService.salvar(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
    }



    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pet existente")
    public ResponseEntity<PetDTO> atualizar(@PathVariable Long id, @RequestBody PetDTO petAtualizado) {
        PetDTO pet = petService.atualizar(id, petAtualizado);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pet existente por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
