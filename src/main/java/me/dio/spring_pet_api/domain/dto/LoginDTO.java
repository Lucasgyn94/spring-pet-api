package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank @Email
        String email,

        @NotBlank
        String senha
) {
}
