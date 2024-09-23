package me.dio.spring_pet_api.domain.dto;


import java.util.List;


public record ClienteDTO (
        Long id,
        String nome,
        String telefone
) { }
