package me.dio.spring_pet_api.service.impl;

import me.dio.spring_pet_api.domain.dto.LoginDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioDTO;
import me.dio.spring_pet_api.domain.dto.UsuarioRegistroDTO;
import me.dio.spring_pet_api.domain.model.Usuario;
import me.dio.spring_pet_api.domain.repository.UsuarioRepository;
import me.dio.spring_pet_api.security.TokenService;
import me.dio.spring_pet_api.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> registrarUsuario(UsuarioRegistroDTO dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        String senhaEncriptada = passwordEncoder.encode(dados.senha());
        Usuario novoUsuario = new Usuario(dados.nome(), dados.email(), senhaEncriptada);

        usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @Override
    public ResponseEntity<String> realizarLogin(LoginDTO dados) {
        UsernamePasswordAuthenticationToken credenciais = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication authentication = this.authenticationManager.authenticate(credenciais);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertUsuarioToDto)
                .collect(Collectors.toList());
    }

    private UsuarioDTO convertUsuarioToDto(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
