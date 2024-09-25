package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record PetDTO(
        Long id,

        @NotBlank
        String nome,

        @NotBlank
        String tipo,

        @NotNull
        Long clienteId
) { }