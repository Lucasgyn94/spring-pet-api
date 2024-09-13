package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.model.Pet;
import me.dio.spring_pet_api.service.ClienteService;
import me.dio.spring_pet_api.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Pet>> listarTodos() {
        List<Pet> pets = petService.listarTodos();
        return ResponseEntity.ok(pets);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        Pet pet = petService.buscarPorId(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<Pet> salvar(@RequestBody Pet pet) {
        Pet petSalvo = petService.salvar(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizar(Long id, Pet petAtualizado) {
        Pet pet = petService.atualizar(id, petAtualizado);

        if (pet.getId() != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
