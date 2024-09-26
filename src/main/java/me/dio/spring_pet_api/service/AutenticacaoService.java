package me.dio.spring_pet_api.service;

import me.dio.spring_pet_api.domain.dto.LoginDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioRegistroDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AutenticacaoService {
    ResponseEntity<String> registrarUsuario(UsuarioRegistroDTO dados);
    ResponseEntity<String> realizarLogin(LoginDTO dados);
    List<UsuarioDTO> listarTodos();
}
