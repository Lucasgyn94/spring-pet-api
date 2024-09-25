package me.dio.spring_pet_api.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteDTO(
        Long id,

        @NotBlank
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String telefone
) { }