package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record VeterinarioDTO(
        Long id,

        @NotBlank
        String nome,

        @NotBlank
        String especialidade
) { }
