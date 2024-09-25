package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicoDTO(
        Long id,

        @NotBlank
        String descricao,

        @NotNull
        @Positive
        BigDecimal preco
) { }
