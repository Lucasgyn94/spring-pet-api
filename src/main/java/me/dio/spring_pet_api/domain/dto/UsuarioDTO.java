package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        Long id,

        @NotBlank
        String nome,

        @NotBlank @Email
        String email
) { }