package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.dto.PetDTO;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    public ResponseEntity<List<PetDTO>> listarTodos() {
        List<PetDTO> pets = petService.listarTodos();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PetDTO> buscarPorId(@PathVariable Long id) {
        PetDTO pet = petService.buscarPorId(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PetDTO> salvar(@RequestBody PetDTO pet) {
        if (pet.clienteDTO() == null || pet.clienteDTO().id() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        PetDTO petSalvo = petService.salvar(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
    }



    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PetDTO> atualizar(@PathVariable Long id, @RequestBody PetDTO petAtualizado) {
        PetDTO pet = petService.atualizar(id, petAtualizado);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
