package me.dio.spring_pet_api.controller;

import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();

        if (clientes != null) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(Long id, Cliente clienteAtualizado) {
        Cliente cliente = clienteService.atualizar(id, clienteAtualizado);

        if (cliente.getId() != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
