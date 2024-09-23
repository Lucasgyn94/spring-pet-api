package me.dio.spring_pet_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


public record ServicoDTO (
        Long id,
        String descricao,
        BigDecimal preco,
        List<PetDTO> pets
){ }
