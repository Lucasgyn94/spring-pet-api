package me.dio.spring_pet_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.spring_pet_api.domain.dto.LoginDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioRegistroDTO;
import me.dio.spring_pet_api.service.impl.AutenticacaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticaçao", description = "Endpoint para gerenciamento de autenticaçao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoServiceImpl autenticacaoService;

    @PostMapping("/registro")
    @Operation(summary = "Criar/Registrar novo usuario")
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioRegistroDTO dados) {
        return autenticacaoService.registrarUsuario(dados);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuarios cadastrados")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO dados) {
        return autenticacaoService.realizarLogin(dados);
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos os usuarios cadastrados")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(autenticacaoService.listarTodos());
    }



}
